import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderGenerator {

    public static void showMenu() {
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
    }

    public static String generateOrderID(String namaRestoran, String tanggalPemesanan, long nomorTelpon) {
        String orderID = "";
        int sumNomorTelpon = 0;
        String stringNomorTelpon = "";

        // While loop untuk nambahin semua digit nomor telpon
        while (nomorTelpon != 0) {
            long digit = nomorTelpon % 10;
            sumNomorTelpon += digit;
            nomorTelpon /= 10;
        }
        // Modulo nomor telpon dengan 100, kalo digitnya sisa 1, tambahin 0
        int modNomorTelpon = sumNomorTelpon % 100;
        if (modNomorTelpon < 10) {
            stringNomorTelpon = "0" + modNomorTelpon;
        } else {
            stringNomorTelpon = "" + modNomorTelpon;
        }
        // Hapus white space di nama restoran, trus ambil 4 digit awal, trus di
        // kapitalin
        orderID += namaRestoran.replace(" ", "").substring(0, 4).toUpperCase();
        // Hapus garis miring di tanggal pemesanaan
        orderID += tanggalPemesanan.replace("/", "");
        orderID += stringNomorTelpon;
        return orderID;
    }

    public static String checkSum(String num) {
        // Bikin string codeCharacterSet untuk nanti dikonversi sesuai index
        // masing-masing character
        String codeCharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        int oddPosition = 0;
        int evenPosition = 0;
        int assignedNumericalValue = 0;
        num += " ";
        // For loop untuk konversi character ke 39 code character dan dipisahkan sesuai
        // index genap dan ganjil
        for (int i = 0; i < num.length() - 1; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < codeCharacterSet.length(); j++) {
                    if (num.substring(i, i + 1).equals(codeCharacterSet.substring(j, j + 1))) {
                        assignedNumericalValue = j;
                        break;
                    } else {
                        continue;
                    }
                }
                evenPosition += assignedNumericalValue;
                assignedNumericalValue = 0;
            } else {
                for (int k = 0; k < codeCharacterSet.length(); k++) {
                    if (num.substring(i, i + 1).equals(codeCharacterSet.substring(k, k + 1))) {
                        assignedNumericalValue = k;
                        break;
                    } else {
                        continue;
                    }
                }
                oddPosition += assignedNumericalValue;
                assignedNumericalValue = 0;
            }
        }
        // Modulo masing masing posisi dengan 36, trus konversi lagi ke awal
        int evenPositionMod = evenPosition % 36;
        int oddPositionMod = oddPosition % 36;
        String evenPositionString = codeCharacterSet.substring(evenPositionMod, evenPositionMod + 1);
        String oddPositionString = codeCharacterSet.substring(oddPositionMod, oddPositionMod + 1);
        return evenPositionString + oddPositionString;
    }

    public static String generateBill(String orderID, String lokasiPengiriman) {
        String lokasiPengirimanUpper = lokasiPengiriman.toUpperCase();
        String availableLocations = "PUTSB  ";
        // For loop untuk ngecek apakah lokasi yang diinginkan tersedia atau tidak, trus
        // tentukan harganya
        for (int i = 0; i < availableLocations.length(); i++) {
            if (lokasiPengirimanUpper.equals(availableLocations.substring(i, i + 1))) {
                if (lokasiPengirimanUpper.equals("P"))
                    return "Rp 10.000";
                else if (lokasiPengirimanUpper.equals("U"))
                    return "Rp 20.000";
                else if (lokasiPengirimanUpper.equals("T"))
                    return "Rp 35.000";
                else if (lokasiPengirimanUpper.equals("S"))
                    return "Rp 40.000";
                else if (lokasiPengirimanUpper.equals("B"))
                    return "Rp 60.000";
            } else if (i == availableLocations.length() - 1) {
                System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
            }
            continue;
        }
        return "error";
    }

    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int chosenMenu = 0;
        String orderIDs = "";
        // Print banner
        showMenu();
        // While loop selama menu 3 belum diinput
        while (chosenMenu != 3) {
            System.out.println("-------------------------");
            System.out.println("Pilih menu: \n1. Generate Order ID\n2. Generate Bill\n3. Keluar");
            System.out.println("-------------------------");
            System.out.print("Pilihan Menu: ");
            chosenMenu = scanner.nextInt();
            scanner.nextLine();
            // If else sesuai menu yang dipilih
            if (chosenMenu == 3)
                break;
            else if (chosenMenu == 1) {
                String namaRestoran = "";
                boolean namaRestoranValidity = false;
                // While loop untuk ngevalidasi nama restoran
                while (namaRestoranValidity == false) {
                    System.out.print("Nama Restoran: ");
                    namaRestoran = scanner.nextLine();
                    if (namaRestoran.replace(" ", "").length() >= 4) {
                        namaRestoranValidity = true;
                    } else {
                        System.out.println(
                                "Nama restoran minimal 4 karakter (tidak termasuk white space). Silahkan masukkan ulang!");
                        continue;
                    }
                }
                String tanggalPemesanan = "";
                boolean tanggalPemesananValidity = false;
                // While loop untuk ngevalidasi tanggal pemesanan
                while (!tanggalPemesananValidity) {
                    System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
                    tanggalPemesanan = scanner.nextLine();
                    String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(tanggalPemesanan);
                    if (matcher.matches()) {
                        tanggalPemesananValidity = true;
                    } else {
                        System.out.println("Tanggal pemesanan dalam bentuk format DD/MM/YYYY");
                    }
                }
                System.out.print("Nomor Telpon: ");
                String nomorTelpon = scanner.nextLine();
                // Ubah nomor telpon jadi long agar bisa dijadiin parameter method
                // generateOrderID
                long nomorTelponLong = Long.parseLong(nomorTelpon);
                // Panggil method generateOrderId dan checkSum trus masukkin ke string orderID
                String orderID = generateOrderID(namaRestoran, tanggalPemesanan, nomorTelponLong);
                String checkSum = checkSum(orderID);
                orderID += checkSum + " ";
                // Kumpulin semua orderID ke dalam string orderIDs
                orderIDs += " " + orderID;
                System.out.println(orderID + " berhasil ditambahkan");
            } else if (chosenMenu == 2) {
                String lokasiPengiriman = "";
                String inputtedOrderID = "";
                boolean orderIDValidity = false;
                // While loop untuk ngevalidasi checksum order id
                while (orderIDValidity == false) {
                    System.out.print("Order ID: ");
                    inputtedOrderID = scanner.nextLine();
                    if (inputtedOrderID.substring(14,16).equals(checkSum(inputtedOrderID.substring(0,14)))) {
                        orderIDValidity = true;
                    } else {
                        System.out.println(inputtedOrderID.substring(14,16));
                        System.out.println(checkSum(inputtedOrderID.substring(0,14)));
                        System.out.println("Order ID tidak tersedia. Silahkan masukkan ulang");
                        continue;
                    }
                }
                String bill = "error";
                // While loop untuk ngevalidasi ketersediaan lokasi pengiriman
                while (bill.equals("error")) {
                    System.out.print("Lokasi pengiriman: ");
                    lokasiPengiriman = scanner.nextLine();
                    bill = generateBill(inputtedOrderID, lokasiPengiriman);
                    if (bill.equals("error"))
                        continue;
                }
                System.out.println("Bill:\n" + bill);
                System.out.println("Order ID: " + inputtedOrderID);
                System.out.println("Tanggal Pemesanan: " + inputtedOrderID.substring(4, 6) + "/"
                        + inputtedOrderID.substring(6, 8) + "/" + inputtedOrderID.substring(8, 12));
                System.out.println("Lokasi Pengiriman: " + lokasiPengiriman.toUpperCase()) ;
            }
            System.out.println("");
        }
        System.out.println("Terima kasih telah menggunakan DePeFood!");
        scanner.close();
    }
}
