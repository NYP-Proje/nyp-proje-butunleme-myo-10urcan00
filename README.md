# Banka Faiz Hesaplama Sistemi 🏦

Merhaba! Bu proje, Beykoz Üniversitesi Nesne Yönelimli Programlama (NYP) dersi bütünleme ödevi için geliştirdiğim bir Java Masaüstü uygulamasıdır. 

Projede JavaFX kullanarak bir kullanıcı arayüzü tasarladım ve verileri kaydetmek/kontrol etmek için SQLite veritabanı kullandım. Uygulamanın temel amacı, farklı hesap türlerine (Mevduat Vadeli Hesap ve Kredi Faiz Maliyeti) göre faiz hesaplamaları yapmaktır.

## 🚀 Kullanılan Teknolojiler
*   **Dil:** Java 21
*   **Arayüz (GUI):** JavaFX (FXML)
*   **Veritabanı:** SQLite
*   **Proje Yönetimi:** Maven
*   **Mimari:** MVC (Model-View-Controller)

## 🧠 OOP (Nesne Yönelimli Programlama) Prensipleri
Projeyi geliştirirken spagetti kod yazmaktan kaçındım ve NYP kurallarına uydum:
*   **Kapsülleme (Encapsulation):** `Hesap` sınıfındaki değişkenleri (ana para, müşteri adı vb.) `private` yapıp, sadece getter/setter metotlarıyla erişime açtım.
*   **Kalıtım (Inheritance):** `VadeliHesap` ve `KrediHesabi` sınıflarını ana `Hesap` sınıfından türettim (`extends`). Böylece kod tekrarından kurtuldum.
*   **Çok Biçimlilik (Polymorphism):** `Hesap` sınıfındaki `faizHesapla()` metodunu alt sınıflarda `@Override` ederek kendi formüllerine göre ezdim. Çalışma zamanında (runtime) kullanıcının seçtiği hesap türüne göre doğru formülün çalışmasını sağladım.

## ⚙️ Kurulum ve Çalıştırma
1. Projeyi bilgisayarınıza indirin (ZIP olarak veya git clone ile).
2. IntelliJ IDEA üzerinden **File -> Open** diyerek proje klasörünü seçin.
3. Maven bağımlılıklarının (JavaFX ve SQLite) yüklenmesini bekleyin.
4. `src/main/java/com/bank/Main.java` dosyasını çalıştırın.

### Giriş Bilgileri
Sistem ilk açıldığında otomatik olarak `banka.db` dosyasını oluşturur ve içine şu admin hesabını ekler:
*   **Kullanıcı Adı:** admin
*   **Şifre:** 1234

## 📸 Ekran Görüntüleri
*(Proje sunumunda gösterilecek FXML tasarımları login ve main ekranları olarak ayarlanmıştır.)*

---
*Geliştirici: Onur (10urcan00) - Beykoz Üniversitesi*
