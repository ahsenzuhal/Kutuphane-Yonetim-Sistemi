# Akıllı Kütüphane Yönetim Sistemi

Spring Boot backend ve Vanilla JavaScript frontend ile geliştirilmiş, uçtan uca çalışan bir dijital kütüphane yönetim platformudur.

<img width="1564" height="909" alt="image" src="https://github.com/user-attachments/assets/81c4c96a-cb55-4432-b5c2-66466602f8f1" />

## İçindekiler
- [Özellikler](#özellikler)
- [Teknolojiler](#teknolojiler)
- [Proje Yapısı](#proje-yapısı)
- [Kurulum ve Çalıştırma](#kurulum-ve-çalıştırma)
- [Uygulama Görselleri](#uygulama-görselleri)
- [Geliştirme Süreci Çözümleri](#geliştirme-süreci-çözümleri)

## Özellikler

### Kullanıcı Paneli
- Öğrenciler kendi kullanıcı adlarıyla sisteme giriş yaparak ödünç aldıkları kitapları listeleyebilir.
- Mevcut kitap koleksiyonu üzerinden anlık ödünç alma işlemi gerçekleştirilebilir.
- İade süreci ve teslim tarihleri dinamik badge sistemi ile takip edilebilir.

### Yönetim Paneli
- Kitap, yazar ve kategori verileri üzerinde tam CRUD kontrolü sağlanır.
- Tüm aktif ödünç işlemleri, öğrenci ve kitap detaylarıyla birlikte görüntülenebilir.
- Rol tabanlı erişim kontrolü (RBAC) sayesinde yetkisiz erişimler engellenir.

## Teknolojiler
- **Backend**: Java 17/21, Spring Boot 3.x, Spring Security (JWT).
- **Veritabanı**: MySQL, Spring Data JPA.
- **Frontend**: HTML5, CSS3 (Dark Mode), Vanilla JavaScript (Fetch API).

## Proje Yapısı

```text
AkilliKutuphane/
├── src/
│   ├── main/
│   │   ├── java/com/kutuphane/AkilliKutuphane/
│   │   │   ├── config/         # Güvenlik ve JWT yapılandırmaları
│   │   │   ├── controller/     # API uç noktaları
│   │   │   ├── dto/            # Veri transfer nesneleri (DTO)
│   │   │   ├── model/          # Veritabanı varlıkları (Entity)
│   │   │   ├── repository/     # Veritabanı erişim katmanı
│   │   │   └── service/        # İş mantığı katmanı
│   │   └── resources/
│   │       ├── static/         # Frontend dosyaları (HTML, JS, CSS)
│   │       └── application.properties # Veritabanı ve uygulama ayarları
└── pom.xml                     # Maven bağımlılıkları

```
## Kurulum ve Çalıştırma

1. **Veritabanı Yapılandırması**
   `src/main/resources/application.properties` dosyasındaki MySQL kullanıcı adı ve şifresini yerel ayarlarınıza göre güncelleyin.

2. **Derleme**
   Proje ana dizininde aşağıdaki komutu çalıştırarak projeyi derleyin:
   ```bash
   mvn clean install
   ```

3. **Çalıştırma**
   Uygulamayı başlatmak için şu komutu kullanın:
   ```bash
   mvn spring-boot:run
   ```

4. **Erişim**
   Tarayıcınızdan
     http://localhost:9090/login.html 
   adresine giderek sisteme giriş yapabilirsiniz.

## Uygulama Görselleri

# Giriş Sayfası 
<img width="1516" height="892" alt="image" src="https://github.com/user-attachments/assets/28f1986a-1773-48e8-898a-ff65caa127f2" />

## Admin Aktif İşlemler Takibi 
<img width="1772" height="881" alt="image" src="https://github.com/user-attachments/assets/db0d33cd-2e13-48f3-ba9d-5f105c63205b" />

## Kitap Arşivi ve Ödünç Alma 
<img width="1664" height="915" alt="image" src="https://github.com/user-attachments/assets/73b95989-4ebc-4c39-a0d4-e597ca9e5b77" />

## Kayıt Ol
<img width="1146" height="852" alt="image" src="https://github.com/user-attachments/assets/92ba3a9a-326d-4f89-af59-c91945fbf1a8" />

## Cezalar
<img width="1581" height="908" alt="image" src="https://github.com/user-attachments/assets/cbf77687-7baf-4a6a-90a0-b4ed7d39b735" />

