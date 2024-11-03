#!/bin/bash
set -e

# Importa o certificado no truststore do Java, caso ainda não esteja importado
if ! keytool -list -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -alias mongodb-ca > /dev/null 2>&1; then
    echo "Importando certificado no truststore Java..."
    keytool -importcert -file /usr/local/share/ca-certificates/mongodb-cert.crt -alias mongodb-ca -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt
else
    echo "Certificado já está importado no truststore."
fi

# Executa o comando padrão do container
exec "$@"
