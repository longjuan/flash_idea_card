mvn -DskipTests=true clean package
docker --version
if [ $? -eq 0 ]; then
  cd ./fic-gateway
  docker build -t fic-gateway:latest ./fic-gateway
  cd ../fic-user
  docker build -t fic-user:latest .
  cd ../fic-auth
  docker build -t fic-auth:latest ./fic-auth
  cd ../fic-kanban
  docker build -t fic-kanban:latest ./fic-kanban
  cd ../fic-search
  docker build -t fic-search:latest ./fic-search
else
    echo "Docker not installed"
fi