FROM alpine:edge

RUN apk update && apk upgrade -a

RUN apk add \
    bash \
    coreutils \
    curl \
    git \
    grep \
    npm \
    openjdk11 \
    procps \
    python3 \
    which

RUN curl https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > /usr/local/bin/lein && \
    chmod +x /usr/local/bin/lein && \
    lein upgrade

RUN git clone https://github.com/nathants/runclj && \
    cd runclj && \
    mv bin/* bin/.lein /usr/local/bin && \
    npm install -g http-server
