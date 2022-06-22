mvn -DskipTests=true clean package
docker --version
if [ $? -eq 0 ]; then
  if [ ${DOCKER_NAMESPACE_NAME} ]; then
    DOCKER_NAMESPACE_HEAD_STR=${DOCKER_NAMESPACE_NAME}/
  else
    DOCKER_NAMESPACE_HEAD_STR=""
  fi
  cd ./fic-gateway
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-gateway:${DOCKER_IMAGE_NAME:-latest}" .
  cd ../fic-user
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-user:${DOCKER_IMAGE_NAME:-latest}" .
  cd ../fic-auth
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-auth:${DOCKER_IMAGE_NAME:-latest}" .
  cd ../fic-kanban
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-kanban:${DOCKER_IMAGE_NAME:-latest}" .
  cd ../fic-search
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-search:${DOCKER_IMAGE_NAME:-latest}" .
else
    echo "Docker not installed"
fi