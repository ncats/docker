package controllers;

import play.mvc.*;
import play.Logger;
import com.typesafe.config.Config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import play.libs.concurrent.HttpExecutionContext;

public class SemRepApi extends Controller {
    final HttpExecutionContext ec;
    final List<String> semRepExec;
    final File work;

    static protected CompletionStage<Result> async (Result result) {
        return supplyAsync (() -> {
                return result;
            });
    }

    @Inject
    public SemRepApi (HttpExecutionContext ec, Config conf) {
        this.ec = ec;
        semRepExec = conf.getStringList("semrep.exec");
        if (semRepExec == null || semRepExec.isEmpty()) {
            Logger.error("Property semrep.exec must be specified!");
            throw new RuntimeException ("No property semrep.exec set!");
        }
        Logger.debug("semRepExec: "+semRepExec);
        work = new File (conf.hasPath("semrep.work")
                         ? conf.getString("semrep.work") : "work");
        work.mkdirs();
        Logger.debug("work: " +work);
        Logger.debug("SemRepApi initialized!");
    }

    Result process (InputStream is) throws Exception {
        Path temp = Files.createTempFile(work.toPath(), "", ".txt");
        try (FileOutputStream fos = new FileOutputStream (temp.toFile())) {
            byte[] b = new byte[1024];
            for (int nb; (nb = is.read(b)) != -1; ) {
                fos.write(b, 0, nb);
            }
        }
        
        List<String> args = new ArrayList<>();
        args.addAll(semRepExec);
        args.add(temp.toString());
        
        Process proc = Runtime.getRuntime().exec(args.toArray(new String[0]));
        int status = proc.waitFor();
        Logger.debug(status+" -- "+args);
        if (0 == status) {
            File out = new File (work, temp.getFileName()+".sem.v1.7");
            if (out.exists())
                return ok(out).as("application/xml");
        }
        
        return internalServerError ("Exec "+args+" return status = "+status);
    }

    Result process (String text) throws Exception {
        return process (new ByteArrayInputStream (text.getBytes("utf8")));
    }

    public CompletionStage<Result> get (String text) {
        return supplyAsync (() -> {
                try {
                    return process (text);
                }
                catch (Exception ex) {
                    return internalServerError (ex.getMessage());
                }
            }, ec.current());
    }

    @BodyParser.Of(value = BodyParser.Text.class)
    public CompletionStage<Result> post () {
        return supplyAsync (() -> {
                try {
                    return process (request().body().asText());
                }
                catch (Exception ex) {
                    return internalServerError (ex.getMessage());
                }
            }, ec.current());
    }
}
