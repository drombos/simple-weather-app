# SDA Projekt praktyczny I, grupa 4

## Prosta aplikacja pogodowa

- pobiera dane pogodowe z kilku API pogodowych
- zapisuje uśrednione prognozy w bazie danych
- pozwala zapisać wiele lokalizacji, dla których będą śledzone prognozy

## Przed uruchomieniem

W repozytorium są pliki `TEMPLATEapi.properties` oraz `TEMPLATEmysql.properties`. Należy je skopiować, usunąć "TEMPLATE"
z nazwy, i wpisać w plikach własne klucze do API oraz nazwę użytkownika / hasło do lokalnej bazy danych. **Plików z tymi
danymi NIE wrzucamy na GitHuba!**

`api.properties`:

```
    accuweather_key=TWÓJ-KLUCZ-API
    openweather_key=TWÓJ-KLUCZ-API
```

`mysql.properties`:

```
    url=URL_TWOJEJ_BAZY_DANYCH
    username=USERNAME_DO_BAZY_DANYCH
    password=HASŁO_DO_BAZY_DANYCH
```

Bez tych plików program nie będzie działać!

## Opis naszej pracy

Dokumentacja naszego _procesu twórczego_ ;-)

https://docs.google.com/document/d/1r7q1d7Byg4oVEWplJrTn-zPdk1cbzkcKvCTvCngDzbM/edit?usp=sharing