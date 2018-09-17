The docker file in this directory builds a docker image for
[SemRep](https://semrep.nlm.nih.gov/). This image depends on the
[MetaMap image](../metamap). Prior to building this image, please
download the following files:

+ [public_semrep_v1.7_Linux.tar.bz2](https://semrep.nlm.nih.gov/download/public_semrep_v1.7_Linux.tar.bz2)
+ [public_semrep_v1.7_lex_db_15_Linux.tar.bz2](https://semrep.nlm.nih.gov/download/public_semrep_v1.7_lex_db_15_Linux.tar.bz2)
+ [public_semrep_v1.7_hier_15_Linux.tar.bz2](https://semrep.nlm.nih.gov/download/public_semrep_v1.7_hier_15_Linux.tar.bz2)

We also need to build a simple REST interface for SemRep as follows:

```
cd semrep-rest-api
./gradlew dist
```

If all goes well, there should be self-contained API bundles
```semrep-rest-api/build/distributions/playBinary.tar``` and
```semrep-rest-api/build/distributions/playBinary.zip```. Now,
assuming the [MetaMap image](../metamap) has already been built, 
simply run the following command to start building the SemRep image:

```
docker build -t ncats/semrep .
```

This build can take half an hour to complete. Once it's done, take the
new image out for a spin as follows:

```
docker run --rm --name=semrep-v1.7 \
       -e ACCEPTED_HOSTS="['127.0.0.1','172.17.0.1']" \
       -p 8066:8066 -p 8067:8067 -t ncats/semrep
```

Note here we're also exposing MetaMap on port 8066. Now try the
following on a separate terminal:

```
curl -X POST -H Content-Type:text/plain \
     --data-binary "modafinil is a novel stimulant that is effective in the treatment of narcolepsy" localhost:8067/
```

You should get something like this as output:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE SemRepAnnotation PUBLIC "-//NLM//DTD SemRep Output//EN" "http://semrep.nlm.nih.gov/DTD/SemRepXML_v1.7.dtd">
<SemRepAnnotation>
 <Document id="D00000000" text="modafinil is a novel stimulant that is effective in the treatment of narcolepsy" >
 <Utterance id="D00000000.tx.1" section="tx" number="1" text="modafinil is a novel stimulant that is effective in the treatment of narcolepsy">
  <Entity id="D00000000.E1" cui="C0066677" name="modafinil" semtypes="orch,phsu" text="modafinil" score="1000" begin="0" end="9" />
  <Entity id="D00000000.E2" cui="C0205314" name="New" semtypes="qlco" text="novel" score="888" begin="15" end="20" />
  <Entity id="D00000000.E3" cui="C0002763" name="Central Nervous System Stimulants" semtypes="phsu" text="stimulant" score="888" begin="21" end="30" />
  <Entity id="D00000000.E4" cui="C1280519" name="Effectiveness" semtypes="qlco" text="effective" score="1000" begin="39" end="48" />
  <Entity id="D00000000.E5" cui="C0001554" name="Administration occupational activities" semtypes="ocac" text="treatment" score="1000" begin="56" end="65" />
  <Entity id="D00000000.E6" cui="C0027404" name="Narcolepsy" semtypes="dsyn" text="narcolepsy" score="1000" begin="69" end="79" />
  <Predication id="D00000000.P1" inferred="true">
   <Subject maxDist="0" dist="0" entityID="D00000000.E1" relSemType="orch" />
   <Predicate type="TREATS" indicatorType="INFER" begin="0" end="30" />
   <Object maxDist="0" dist="0" entityID="D00000000.E6" relSemType="dsyn" />
  </Predication>
  <Predication id="D00000000.P2">
   <Subject maxDist="0" dist="0" entityID="D00000000.E1" relSemType="orch" />
   <Predicate type="ISA" indicatorType="SPEC" begin="0" end="30" />
   <Object maxDist="0" dist="0" entityID="D00000000.E3" relSemType="phsu" />
  </Predication>
  <Predication id="D00000000.P3">
   <Subject maxDist="3" dist="2" entityID="D00000000.E3" relSemType="phsu" />
   <Predicate type="TREATS" indicatorType="NOM" begin="56" end="65" />
   <Object maxDist="1" dist="1" entityID="D00000000.E6" relSemType="dsyn" />
  </Predication>
 </Utterance>
</Document>
</SemRepAnnotation>
```
