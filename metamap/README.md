The docker file in this directory builds a docker image for
[MetaMap](https://metamap.nlm.nih.gov), a resource that maps text
into UMLS concepts. Before building the image, please download the
latest version of MetaMap (Full download) and corresponding Java API
[here](https://metamap.nlm.nih.gov/MainDownload.shtml). Assuming both
```*.tar.bz2``` downloaded files are in the same directory as the
Docker file, run the following command to begin building the image:

```
docker build -t ncats/metamap .
```

The argument to ```-t``` can be anything. This command will take a bit
of time to complete. Use the following command to see the image:

```
docker images
```

Now invoking the image as follows:

```
docker run --rm -t -p 18066:18066 ncats/metamap
```

If all goes well, you should see something similar in your console:

```
Server options: [port(8066),accepted_hosts(['127.0.0.1'])]
Established connection $stream(140567729457696) to TAGGER Server on localhost.
Options:[lexicon,'Z']
Args:[db,'2016AA']
port:8066
```
