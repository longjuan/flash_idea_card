mvn -DskipTests=true clean package
docker --version
if [ $? -eq 0 ]; then
  if [ ${DOCKER_NAMESPACE_NAME} ]; then
    DOCKER_NAMESPACE_HEAD_STR=${DOCKER_NAMESPACE_NAME}/
  else
    DOCKER_NAMESPACE_HEAD_STR=""
  fi
  if [ ${DOCKER_IMAGE_NAME} ]; then
      DOCKER_IMAGE_NAME_STR=${DOCKER_IMAGE_NAME}
  else
      DOCKER_IMAGE_NAME_STR="latest"
  fi
  cd ./fic-gateway
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-gateway:${DOCKER_IMAGE_NAME}" .
  cd ../fic-user
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-user:${DOCKER_IMAGE_NAME}" .
  cd ../fic-auth
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-auth:${DOCKER_IMAGE_NAME}" .
  cd ../fic-kanban
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-kanban:${DOCKER_IMAGE_NAME}" .
  cd ../fic-search
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-search:${DOCKER_IMAGE_NAME}" .
  cd ../fic-mail
  docker build -t "${DOCKER_NAMESPACE_HEAD_STR}fic-mail:${DOCKER_IMAGE_NAME}" .
else
    echo "Docker not installed"
fi