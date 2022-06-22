mvn -DskipTests=true clean package
docker --version
if [ $? -eq 0 ]; then
  if [ ${DOCKER_NAMESPACE_NAME} ]; then
    DOCKER_NAMESPACE_HEAD_STR=${DOCKER_NAMESPACE_NAME}/
  else
    DOCKER_NAMESPACE_HEAD_STR=""
  fi
  cd ./fic-gateway
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}"fic-gateway:latest ./fic-gateway
  cd ../fic-user
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}"fic-user:latest .
  cd ../fic-auth
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}"fic-auth:latest ./fic-auth
  cd ../fic-kanban
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}"fic-kanban:latest ./fic-kanban
  cd ../fic-search
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}"fic-search:latest ./fic-search
else
    echo "Docker not installed"
fi