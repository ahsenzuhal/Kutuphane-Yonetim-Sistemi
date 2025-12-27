# Akıllı Kütüphane Yönetim Sistemi

Spring Boot backend ve Vanilla JavaScript frontend ile geliştirilmiştir.

<img width="1915" height="948" alt="image" src="https://github.com/user-attachments/assets/e5303276-8323-4e2a-98a6-1440e67e7b46" />



## İçindekiler

- [Özellikler](#özellikler)
- [Teknolojiler](#teknolojiler)
- [Kurulum](#kurulum)
- [Proje Yapısı](#proje-yapısı)
- [API Dokümantasyonu](#api-dokümantasyonu)
- [Frontend Sayfaları](#frontend-sayfaları)
- [Güvenlik](#güvenlik)
- [Veritabanı Şeması](#veritabanı-şeması)
- [Kullanım Kılavuzu](#kullanım-kılavuzu)

##  Özellikler

### Backend Özellikleri
- ✅ JWT tabanlı kimlik doğrulama ve yetkilendirme
- ✅ Rol tabanlı erişim kontrolü (ADMIN/USER)
- ✅ Ödünç alma/iade işlem takibi (OduncIslem Entity)
# Akıllı Kütüphane — Yapılan Değişiklikler ve Kurulum

Bu depo üzerinde kayıt (register) akışını iyileştirdim ve frontend kayıt sayfasını modern, erişilebilir bir tasıma güncelledim. Aşağıda yapılan değişiklikler, nasıl derleyip çalıştıracağınız, potansiyel veritabanı değişiklikleri ve test adımları yer alır.

## Özet (Kısa)
- Sunucu tarafında `RegisterRequest` DTO'su genişletildi (isim, email, doğrulama anotasyonları).
- `Kullanici` (model) sınıfına `isim` ve `email` alanları eklendi (email için `unique=true`).
- `/auth/register` endpoint'i `@Valid` ile doğrulama yapar; alan bazlı hata yanıtları döner; duplicate kullanıcı adı için 409 döndürülür.
- `register.html` güncellendi: modern tasarım, inline doğrulamalar, şifre güç göstergesi ve şifre onayı eklendi.

## Değiştirilen Dosyalar
- [src/main/java/com/kutuphane/AkilliKutuphane/dto/RegisterRequest.java](src/main/java/com/kutuphane/AkilliKutuphane/dto/RegisterRequest.java)
- [src/main/java/com/kutuphane/AkilliKutuphane/model/Kullanici.java](src/main/java/com/kutuphane/AkilliKutuphane/model/Kullanici.java)
- [src/main/java/com/kutuphane/AkilliKutuphane/controller/AuthController.java](src/main/java/com/kutuphane/AkilliKutuphane/controller/AuthController.java)
- [src/main/resources/static/register.html](src/main/resources/static/register.html)

### Adım Adım Yapılanlar
1. İnceleme: `AuthController`, `KullaniciService`, `RegisterRequest`, `Kullanici`, ve `register.html` incelendi; DTO ve frontend alan uyuşmazlıkları belirlendi.
2. DTO genişletme: `RegisterRequest` içine `isim`, `email` eklendi ve Jakarta Validation anotasyonları (`@NotBlank`, `@Email`, `@Size`) kullanıldı.
3. Model güncelleme: `Kullanici`'ya `isim` ve `email` eklendi; `email` için `unique=true` konuldu. (Not: mevcut veritabanı şemasına migration gerekebilir.)
4. Controller güncelleme: `/auth/register` endpoint'i artık `@Valid` ile doğruluyor, `BindingResult` ile alan hatalarını JSON olarak döndürüyor, duplicate kullanıcı adı için 409 dönüyor ve kayıt sırasında isim/email kaydediliyor.
5. Frontend güncelleme: Kayıt formuna `passwordConfirm`, parola güç göstergesi, inline hata alanları ve daha iyi yükleme/başarı/başarısızlık geri bildirimleri eklendi.

## Bilinen Durumlar / Çözülmesi Gerekenler
- Bu ortamda Maven build çalıştırılamadı: `JAVA_HOME` ortam değişkeni tanımlı değil. Bu nedenle ben tüm derleme hatalarını doğrudan çalıştırarak doğrulayamadım. Aşağıda nasıl derleyeceğinize dair adımlar var.
- `Kullanici` modeline eklenen `email` için `unique=true` eklendi; mevcut veritabanında bu sütun yoksa migration (DDL) gerekiyor. Aksi takdirde uygulama çalışırken tablo/sütun bulunamaması veya constraint hatası alabilirsiniz.

## Derleme ve Çalıştırma (Windows)
1. Java 21 veya uyumlu JDK kurulu olduğundan emin olun ve `JAVA_HOME` ayarlayın.
    PowerShell örneği:
```powershell
setx JAVA_HOME "C:\\Program Files\\Java\\jdk-21" -m
# Yeni terminal açın
```
2. Proje kökünde (version4) Maven Wrapper ile derleyin. Geliştirme amacıyla MySQL kurulu değilse `dev` profili ile H2 in-memory veritabanı kullanabilirsiniz:
```powershell
cd "c:\\Users\\batma\\OneDrive - Karadeniz Teknik Üniversitesi\\Masaüstü\\ahsen\\version4"
.\\mvnw.cmd clean package -DskipTests=true

-- `dev` profili ile çalıştırma (H2 kullanılacak):

```powershell
.\\mvnw.cmd -Dspring-boot.run.profiles=dev spring-boot:run
```
```
3. Uygulamayı çalıştırın:
```powershell
.\\mvnw.cmd spring-boot:run
```

## Veritabanı Migration (Hızlı SQL örneği)
Eğer `kullanicilar` tablonuzda `isim` ve `email` alanları yoksa aşağıdaki SQL ile ekleyebilirsiniz (MySQL örneği):

```sql
ALTER TABLE kullanicilar
   ADD COLUMN isim VARCHAR(100),
   ADD COLUMN email VARCHAR(255);

-- Email için unique constraint ekleme (önce duplicate kayıtları temizleyin)
ALTER TABLE kullanicilar
   ADD CONSTRAINT uq_kullanici_email UNIQUE (email);
```

## API Test Örneği (curl)
```bash
curl -X POST http://localhost:9090/auth/register \
   -H 'Content-Type: application/json' \
   -d '{"isim":"Ahmet Deniz","kullaniciAdi":"ahmet","email":"ahmet@example.com","sifre":"P4ssw0rd","rol":"USER"}'
```
Başarılıysa `AuthResponse` (token, kullanıcı adı, rol) dönecektir. Hatalı isteklerde alan hataları JSON gövdesiyle dönecektir.

## Sonraki Adımlar (Öneriler)
- Uçtan uca test: Build + run + kayıt testi ve hata inceleme.
- E-posta doğrulama (activation link) eklenmesi.
- Rate-limiting veya CAPTCHA ile bot koruması.
- Eğer isterseniz Flyway/Liquibase migration script'leri hazırlayıp ekleyebilirim.

# Akıllı Kütüphane Yönetim Sistemi

Spring Boot backend ve Vanilla JavaScript frontend ile geliştirilmiştir.

<img width="1915" height="948" alt="image" src="https://github.com/user-attachments/assets/e5303276-8323-4e2a-98a6-1440e67e7b46" />



## İçindekiler

- [Özellikler](#özellikler)
- [Teknolojiler](#teknolojiler)
- [Kurulum](#kurulum)
- [Proje Yapısı](#proje-yapısı)
- [API Dokümantasyonu](#api-dokümantasyonu)
- [Frontend Sayfaları](#frontend-sayfaları)
- [Güvenlik](#güvenlik)
- [Veritabanı Şeması](#veritabanı-şeması)
- [Kullanım Kılavuzu](#kullanım-kılavuzu)

##  Özellikler

### Backend Özellikleri
- ✅ JWT tabanlı kimlik doğrulama ve yetkilendirme
- ✅ Rol tabanlı erişim kontrolü (ADMIN/USER)
- ✅ Ödünç alma/iade işlem takibi (OduncIslem Entity)
# Akıllı Kütüphane — Yapılan Değişiklikler ve Kurulum

Bu depo üzerinde kayıt (register) akışını iyileştirdim ve frontend kayıt sayfasını modern, erişilebilir bir tasıma güncelledim. Aşağıda yapılan değişiklikler, nasıl derleyip çalıştıracağınız, potansiyel veritabanı değişiklikleri ve test adımları yer alır.

## Özet (Kısa)
- Sunucu tarafında `RegisterRequest` DTO'su genişletildi (isim, email, doğrulama anotasyonları).
- `Kullanici` (model) sınıfına `isim` ve `email` alanları eklendi (email için `unique=true`).
- `/auth/register` endpoint'i `@Valid` ile doğrulama yapar; alan bazlı hata yanıtları döner; duplicate kullanıcı adı için 409 döndürülür.
- `register.html` güncellendi: modern tasarım, inline doğrulamalar, şifre güç göstergesi ve şifre onayı eklendi.

## Değiştirilen Dosyalar
- [src/main/java/com/kutuphane/AkilliKutuphane/dto/RegisterRequest.java](src/main/java/com/kutuphane/AkilliKutuphane/dto/RegisterRequest.java)
- [src/main/java/com/kutuphane/AkilliKutuphane/model/Kullanici.java](src/main/java/com/kutuphane/AkilliKutuphane/model/Kullanici.java)
- [src/main/java/com/kutuphane/AkilliKutuphane/controller/AuthController.java](src/main/java/com/kutuphane/AkilliKutuphane/controller/AuthController.java)
- [src/main/resources/static/register.html](src/main/resources/static/register.html)

### Adım Adım Yapılanlar
1. İnceleme: `AuthController`, `KullaniciService`, `RegisterRequest`, `Kullanici`, ve `register.html` incelendi; DTO ve frontend alan uyuşmazlıkları belirlendi.
2. DTO genişletme: `RegisterRequest` içine `isim`, `email` eklendi ve Jakarta Validation anotasyonları (`@NotBlank`, `@Email`, `@Size`) kullanıldı.
3. Model güncelleme: `Kullanici`'ya `isim` ve `email` eklendi; `email` için `unique=true` konuldu. (Not: mevcut veritabanı şemasına migration gerekebilir.)
4. Controller güncelleme: `/auth/register` endpoint'i artık `@Valid` ile doğruluyor, `BindingResult` ile alan hatalarını JSON olarak döndürüyor, duplicate kullanıcı adı için 409 dönüyor ve kayıt sırasında isim/email kaydediliyor.
5. Frontend güncelleme: Kayıt formuna `passwordConfirm`, parola güç göstergesi, inline hata alanları ve daha iyi yükleme/başarı/başarısızlık geri bildirimleri eklendi.

## Bilinen Durumlar / Çözülmesi Gerekenler
- Bu ortamda Maven build çalıştırılamadı: `JAVA_HOME` ortam değişkeni tanımlı değil. Bu nedenle ben tüm derleme hatalarını doğrudan çalıştırarak doğrulayamadım. Aşağıda nasıl derleyeceğinize dair adımlar var.
- `Kullanici` modeline eklenen `email` için `unique=true` eklendi; mevcut veritabanında bu sütun yoksa migration (DDL) gerekiyor. Aksi takdirde uygulama çalışırken tablo/sütun bulunamaması veya constraint hatası alabilirsiniz.

## Derleme ve Çalıştırma (Windows)
1. Java 21 veya uyumlu JDK kurulu olduğundan emin olun ve `JAVA_HOME` ayarlayın.
    PowerShell örneği:
```powershell
setx JAVA_HOME "C:\\Program Files\\Java\\jdk-21" -m
# Yeni terminal açın
```
2. Proje kökünde (version4) Maven Wrapper ile derleyin. Geliştirme amacıyla MySQL kurulu değilse `dev` profili ile H2 in-memory veritabanı kullanabilirsiniz:
```powershell
cd "c:\\Users\\batma\\OneDrive - Karadeniz Teknik Üniversitesi\\Masaüstü\\ahsen\\version4"
.\\mvnw.cmd clean package -DskipTests=true

-- `dev` profili ile çalıştırma (H2 kullanılacak):

```powershell
.\\mvnw.cmd -Dspring-boot.run.profiles=dev spring-boot:run
```
```
3. Uygulamayı çalıştırın:
```powershell
.\\mvnw.cmd spring-boot:run
```

## Veritabanı Migration (Hızlı SQL örneği)
Eğer `kullanicilar` tablonuzda `isim` ve `email` alanları yoksa aşağıdaki SQL ile ekleyebilirsiniz (MySQL örneği):

```sql
ALTER TABLE kullanicilar
   ADD COLUMN isim VARCHAR(100),
   ADD COLUMN email VARCHAR(255);

-- Email için unique constraint ekleme (önce duplicate kayıtları temizleyin)
ALTER TABLE kullanicilar
   ADD CONSTRAINT uq_kullanici_email UNIQUE (email);
```

## API Test Örneği (curl)
```bash
curl -X POST http://localhost:9090/auth/register \
   -H 'Content-Type: application/json' \
   -d '{"isim":"Ahmet Deniz","kullaniciAdi":"ahmet","email":"ahmet@example.com","sifre":"P4ssw0rd","rol":"USER"}'
```
Başarılıysa `AuthResponse` (token, kullanıcı adı, rol) dönecektir. Hatalı isteklerde alan hataları JSON gövdesiyle dönecektir.

## Sonraki Adımlar
- Uçtan uca test: Build + run + kayıt testi ve hata inceleme.
- E-posta doğrulama (activation link) eklenmesi.
- Rate-limiting veya CAPTCHA ile bot koruması.
- Eğer isterseniz Flyway/Liquibase migration script'leri hazırlayıp ekleyebilirim.



