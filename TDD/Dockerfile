FROM php:8.2-cli

RUN apt-get update && apt-get install -y \
    git \
    unzip \
    && rm -rf /var/lib/apt/lists/*

COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /app

# ✅ ZUERST alles kopieren
COPY . .

# ✅ DANN composer install
RUN composer install --no-interaction

CMD ["./vendor/bin/phpunit"]
