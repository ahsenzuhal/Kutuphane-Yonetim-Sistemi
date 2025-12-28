# Akıllı Kütüphane Yönetim Sistemi

Spring Boot backend ve Vanilla JavaScript frontend ile geliştirilmiş, uçtan uca çalışan bir dijital kütüphane yönetim platformudur.

<img width="1915" height="948" alt="Kütüphane Sistemi Önizleme" src="https://github.com/user-attachments/assets/e5303276-8323-4e2a-98a6-1440e67e7b46" />

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
(Buraya login.html sayfasının ekran görüntüsünü ekleyin)

## Kullanıcı Kitap Listesi 
(Buraya ödünç alınan kitapların listelendiği my-books.html görüntüsünü ekleyin)

## Kitap Arşivi ve Ödünç Alma 
(Buraya tüm kitapların kartlar şeklinde göründüğü books.html görüntüsünü ekleyin)

## Admin Aktif İşlemler Takibi 
(Buraya admin panelindeki aktif ödünç takip tablosunu ekleyin)