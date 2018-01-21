# Example DDD techniques like a Bounded Contexts, building blocks, and Hexagonal architecture, BDD test (Spock framework)




Biznes polega na tym, że istnieje warsztat świadczący usługi naprawy wynajmowanych samochodów.
Wynajem jest długo terminowy na podstawie kontraktów - umowa papierowa.
Kontrakt określa:
- darmowe naprawy w ramach gwarancji, do limitu ustalonego w kontrakcie,
- płatne naprawy po przekroczeniu limitów.
Limity napraw oraz kwoty za płatne naprawy są negocjowane, więc każdy kontrakt może być inny.
Czyli kontrakt per klient.
Po naprawie samochodu klient otrzymuje rozliczenie, zawierające kwotę za wykonane naprawy.
Rozliczenie może zawierać wiele napraw: bezpłatnych i płatnych.

Historia do zaimplementowania w systemie.
- Zarejestrowanie zlecenia naprawy.
- Pracownik warsztatu podejmuje zlecenie - przypisanie pracownika do zlecenia naprawy.
- Zamknięcie zlecenia i zarejestrowanie napraw wykonanych przez pracownika warsztatu w ramach zlecenia.
- Wystawienie rozliczenia, zawierające naprawy bezpłatne i płatne.

Mamy tu kontrakt z klientem (wynajmujący auta). Kontrakt określa limity i ceny za naprawy (jakieś typy napraw).
Naprawy są wykonywane w ramach zlecenia. Podczas zamykania zlecenia pracownik warsztatu podaje wykonane naprawy.
Wykonane naprawy pomniejszają dostępne bezpłatne naprawy, te zapisane w kontrakcie.




TODO:

 + rozszerzyć opis domeny. Przyjać założenia/reguły biznesowe - łatwiej wydzielić granice agregatów.
 + rozszerzyc model domenowy
 + zastanowic sie nad komunikacja pomiędzy BC - za dużo klas w pakiecie publishedlanguage
 + dopisac brakujaca logike i testy (akceptacyjne)
 + brak endpointow
 + cqrs aby mieć model do odczytu.
 + Strategic Patternd in DDD ?
 + Zastanowić się czy agregat w domain może być public.
 + Utworzyć odpowiednie adnotacje dla bytów domentowych np. Aggregate oraz technicznych
 + Wystawić interface do repo jako public w domenie.
 + Zaimplementować BC - invoice(invoicing)
 + Zdarzenia w obrębie jednego modułu nie mają sensu... (Zapis dwóch agregatów per transakcja... WorkOrderServiceFacade    addRepairToOrder)
 + Poprawic implementacje zapisu repo w pamieci (Brak nadawania id dla encji w relacji)
 + Dodac JPA mapping
 + Klasy DomainException i BaseEntity nie powinny być w sharedkernel.
