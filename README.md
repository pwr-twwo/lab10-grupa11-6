Instrukcja użycia:

1. Utwórz instancję ECR
2. Uruchom terraform apply uzupełniając dane ECRa
3. Uruchom aplikację Jenkins powstałą w wyniku użycia terraform
4. Zdefiniuj node Build, łączący się do maszyny budującej
5. Zdefiniuj node Built-In łączący się do maszyny w której deployowana będzie aplikacja
6. Zdefinuj nowy Job typu Pipeline, zaznacz Github project oraz GitHub hook trigger for GITScm polling.
 - pipeline zdefiniuj jako Pipeline script from SCM
7. Utwórz Webhooka w repozytorium, w którym dodaj Payload URL z publicznym adresem IP maszyny EC2 Jenkins np. http://3.239.68.60:8080/github-webhook/
   - zaznacz disable SSL verification
   - zaznacz Just the push event
   - zaznacz Active


Uwagi:
Przy tworzeniu node dla maszyny budującej w Jenkins wybraliśmy
Host Key Verification Strategy = Non verifying Verification Strategy

Problemy przy implementacji:

Podczas tworzenia komend shellowych w krokach Jenkinsfile'a bardzo czasochłonne było zdiagnozowanie, że komendy aws'owe muszą być wykonywane w wielolinijkowy sposób. Na przykład tak:                 
sh '''
      aws --version
      aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_REPO_USER_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com
'''

Podczas tworzenia maszyn na początku użyliśmy generowania kluczy przy pomocy terraforma w taki sposób, co sprawiło dużo problemów z połączeniem.

resource "tls_private_key" "example" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "jenkins_key" {
  key_name   = "jenkins-key"
  public_key = tls_private_key.example.public_key_openssh
}

Rozwiązaniem było wygenerowanie klucza za pomocą komendy: ssh-keygen -t rsa -b 4096 -C "password" -f ~/.ssh/jenkins_key


# Instrukcja Użycia

## 1. Utwórz instancję ECR
Rozpocznij od utworzenia Amazon Elastic Container Registry (ECR), które będzie przechowywało obrazy Docker.


## 2. Uruchom `terraform apply`
Wykonaj komendę `terraform apply`, uzupełniając dane dotyczące ECR.


## 3. Uruchom aplikację Jenkins
Po zakończeniu działania Terraform uruchom aplikację Jenkins, która została utworzona w procesie.


## 4. Zdefiniuj `node Build`
Utwórz w Jenkinsie node o nazwie `Build`, który łączy się do maszyny budującej.
Przy tworzeniu node dla maszyny budującej w Jenkins wybraliśmy
Host Key Verification Strategy = Non verifying Verification Strategy

## 5. Zdefiniuj `node Built-In`
Utwórz w Jenkinsie node o nazwie `Built-In`, który łączy się do maszyny, na której będzie deployowana aplikacja.


## 6. Zdefiniuj nowy Job typu Pipeline
1. W Jenkinsie utwórz nowy Job typu **Pipeline**.
2. W sekcji konfiguracji zaznacz:
   - **GitHub project**.
   - **GitHub hook trigger for GITScm polling**.
3. Ustaw konfigurację pipeline jako **Pipeline script from SCM**.


## 7. Utwórz webhook w repozytorium
1. W repozytorium GitHub dodaj webhooka.
2. Wypełnij pole **Payload URL** publicznym adresem IP maszyny EC2 z Jenkinsa, np.:  
   `http://3.239.68.60:8080/github-webhook/`.
3. Zaznacz:
   - **Disable SSL verification**.
   - **Just the push event**.
   - **Active**.

---

# Uwagi

- Podczas tworzenia node'a dla maszyny budującej w Jenkinsie należy ustawić:  
  **Host Key Verification Strategy** = **Non verifying Verification Strategy**.

---

# Problemy przy implementacji

### 1. Shell w Jenkinsfile
Podczas tworzenia komend shellowych w krokach Jenkinsfile'a bardzo czasochłonne było zdiagnozowanie, że komendy aws'owe muszą być wykonywane w wielolinijkowy sposób. Na przykład tak:

```bash
sh '''
aws --version
aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_REPO_USER_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com
'''
```
## 2. Generowanie kluczy w Terraform

Podczas tworzenia maszyn na początku użyliśmy generowania kluczy przy pomocy terraforma w taki sposób, co sprawiło dużo problemów z połączeniem. Problematyczny kod:

```hcl
resource "tls_private_key" "example" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "jenkins_key" {
  key_name   = "jenkins-key"
  public_key = tls_private_key.example.public_key_openssh
}
