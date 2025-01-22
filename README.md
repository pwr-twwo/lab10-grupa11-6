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
```
## 3. Hostowanie statycznego frontendu na S3Bucket
Problemem było połączenie się ze strony hostowanej na S3Bucket do naszego backendu, który obsługiwał tylko HTTP. Rozwiązaniem było zdefniowanie polityk i obsługa CORS na backendzie i uruchamianie strony po HTTP.
