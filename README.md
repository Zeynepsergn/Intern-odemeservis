# Environment Variables
---------------------------
<pre>
PORT=7010;
DB_URL=jdbc:postgresql://localhost:5432/gib_staj;
DB_USER=postgres;
DB_PASS=123;
CONTEXT_PATH=/odeme-server;
DB_SCHEMA=gsths;

SWAGGER_TITLE=Ödeme Servisi;
SWAGGER_VERSION=1.0;
SWAGGER_DSC=Ödeme service ile ilgili islemleri yapan servistir;
SWAGGER_TOS=https://gib.gov.tr;
SWAGGER_NAME=Hüseyin Enes Gökçe;
SWAGGER_EMAIL=hüseyin.enes.gokce@gelirler.gov.tr;

RABBITMQ_HOST=localhost;
RABBITMQ_PORT=5672;
RABBITMQ_USER=guest;
RABBITMQ_PASS=guest;
RABBITMQ_EXCHANGE=tahsilat.exchange;
RABBITMQ_ROUTING=odemerk.*;

FPOS_URL=http://localhost:7030/fpos-server/odeme_servis;
SPOS_URL=http://localhost:7060/spos-server/;
NAKIT_URL=http://localhost:9090/api/nakit-odeme/odeme-al;
</pre>