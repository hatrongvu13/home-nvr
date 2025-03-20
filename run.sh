mvn archetype:create-from-project

cd target/generated-sources/archetype
mvn install

mvn archetype:generate \
    -DarchetypeGroupId=com.xxx \
    -DarchetypeArtifactId=archetype-custom-archetype \
    -DarchetypeVersion=0.0.1-SNAPSHOT \
    -DgroupId=com.xxx \
    -DartifactId=new-app \
    -Dversion=0.0.1-SNAPSHOT

