# Environment Variables
---------------------------
<pre>
PORT=7070;
DB_URL=jdbc:postgresql://localhost:5432/PROJE;
DB_USER=postgres;
DB_PASS=123;
CONTEXT_PATH=/odeme-server;

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
SPOS_URL=http://10.251.34.57:7060/spos-server/;
NAKIT_URL=http://10.251.34.57:9090/nakit-server/;
</pre>