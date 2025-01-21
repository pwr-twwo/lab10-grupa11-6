Instrukcja użycia:

1. Utwórz instancję ECR
2. Uruchom terraform apply uzupełniając dane ECRa
3. Uruchom aplikację Jenkins powstałą w wyniku użycia terraform
4. Zdefiniuj node Build, łączący się do maszyny budującej
5. Zdefiniuj node Built-In łączący się do maszyny w której deployowana będzie aplikacja




Uwagi:
Przy tworzeniu node dla maszyny budującej w Jenkins wybraliśmy
Host Key Verification Strategy = Non verifying Verification Strategy

