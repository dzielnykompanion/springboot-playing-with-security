# springboot-playing-with-security

Aplikacja hosting zdjec. 
Aplikacja posiada dwa rodzaje uzytkownikow, ktorych przechowuje w bazie danych - administrator oraz user. Po zalogowaniu
administrator moze wrzucac zdjecia za pomoca darmowego zewnetrzengo api (cloudinary.com, limit 1000mb) ( localhost:8080/upload ), 
user moze tylko przegladac zdjecia - ( localhost:8080/gallery ).

