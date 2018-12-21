**To build APP Docker image use this command**

docker build -t IMAGE_NAME .

**To build Database Docker image use this command from project home directory**

 docker build -t IMAGE_NAME -f src/main/resources/db/Dockerfile .