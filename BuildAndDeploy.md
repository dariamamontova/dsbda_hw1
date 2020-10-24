###Build and deploy manual

### Requirements

- maven
- gitJDK 1.8
- Hadoop 2.10.0
- snappy-1.1.0
- python 3.6 (for sample data generation)

### Preparation

Download sources

```
git clone https://gitlab.com/mamontova.dasha/dsbda_hw1.git
```

### Build

In the project directory:

```
mvn install
```

### Deploy

Start HDFS and YARN

```
cd /opt/hadoop-2.10.0/
sbin/start-dfs.sh
sbin/start-yarn.sh
```

Load the input data into the hdfs-directory.

```
hdfs dfs -put <local path> <DFS path>
```

Start the application

```
yarn jar ./target/homework1-1.0-SNAPSHOT-jar-with-dependencies.jar <input DFS directory> <output DFS directory>
```

### Generate sample data (optional)

Generate input data (logs)

```
cd homework1
python generator.py <number of lines in file> <file name>

```

To see the output file run this command:

```
hdfs dfs -text output/part-r-00000
```
