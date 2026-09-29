/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

/**
 * Chương trình kiểm thử theo kịch bản 15 bước của đề bài (phần C),
 * sau đó kiểm tra các trường hợp biên.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("1. Tao 2 Employee");
        Employee e1 = new Employee("E01", "Nguyen Van An");
        Employee e2 = new Employee("E02", "Tran Thi Binh", 12000000);
        e1.displayInfo();
        e2.displayInfo();

        System.out.println("\n2. Tao 2 SoftwareEngineer");
        SoftwareEngineer se1 = new SoftwareEngineer("SE01", "Le Van Cuong", "Java");
        SoftwareEngineer se2 = new SoftwareEngineer("SE02", "Pham Thi Dung", 20000000, "C++", 3000000);
        se1.displayInfo();
        se2.displayInfo();

        System.out.println("\n3. Tang luong E02 them 1500000");
        e2.increaseSalary(1500000);
        e2.displayInfo();

        System.out.println("\n4. Tang luong SE02 them 10%");
        se2.increaseSalary(10, true);
        se2.displayInfo();

        System.out.println("\n5. Tao nhom P01 chua co truong nhom");
        ProjectTeam team1 = new ProjectTeam("P01", "Quan ly kho");
        team1.displayTeam();

        System.out.println("\n6. Them E01, E02 vao nhom");
        System.out.println("Them E01: " + team1.addMember(e1));
        System.out.println("Them E02: " + team1.addMember(e2));

        System.out.println("\n7. Them SE02 va dat lam truong nhom");
        System.out.println("Them SE02: " + team1.addMember(se2, true));

        System.out.println("\n8. Them lai E01");
        System.out.println("Them E01: " + team1.addMember(e1));

        System.out.println("\n9. Hien thi nhom");
        team1.displayTeam();

        System.out.println("\n10. Tong chi phi thang cua nhom");
        System.out.printf("Tong chi phi: %.0f%n", team1.calculateTotalMonthlyCost());

        System.out.println("\n11. Xoa truong nhom SE02");
        System.out.println("Xoa SE02: " + team1.removeMember("SE02"));

        System.out.println("\n12. Doi truong nhom sang SE01 roi xoa SE02");
        System.out.println("Doi truong nhom: " + team1.changeLeader(se1));
        System.out.println("Xoa SE02: " + team1.removeMember("SE02"));
        team1.displayTeam();

        System.out.println("\n13. Tao nhom P02 voi truong nhom E02 (E02 dang o nhom P01)");
        try (ProjectTeam team2 = new ProjectTeam("P02", "Ung dung di dong", e2)) {
            System.out.println("Them SE02: " + team2.addMember(se2));
            team2.displayTeam();
            System.out.println("E02 o P01: " + team1.contains("E02"));
            System.out.println("E02 o P02: " + team2.contains("E02"));

            System.out.println("\n14. Ket thuc khoi lenh, nhom P02 bi huy");
        }

        System.out.println("\n15. Nhan su cua P02 van con sau khi nhom bi huy");
        e2.displayInfo();
        se2.displayInfo();

        System.out.println("\n--- Truong hop bien ---");
        testEdgeCases(team1, e1);

        System.out.println("\n--- Ket thuc chuong trinh ---");
        team1.close();
        se2.close();
        se1.close();
        e2.close();
        e1.close();
    }

    /**
     * Các trường hợp biên: constructor mặc định, dữ liệu không hợp lệ,
     * tăng lương với giá trị không dương, thêm trùng mã, xóa mã không tồn tại,
     * thêm null, nhóm rỗng.
     */
    @SuppressWarnings("resource")
    static void testEdgeCases(ProjectTeam team, Employee e1) {
        Employee unknown = new Employee();
        unknown.displayInfo();
        unknown.close();

        try {
            new Employee("", "Ten");
        } catch (IllegalArgumentException ex) {
            System.out.println("Loi: " + ex.getMessage());
        }

        try {
            new Employee("E09", "Ten", -1);
        } catch (IllegalArgumentException ex) {
            System.out.println("Loi: " + ex.getMessage());
        }

        try {
            new SoftwareEngineer("SE09", "Ten", "");
        } catch (IllegalArgumentException ex) {
            System.out.println("Loi: " + ex.getMessage());
        }

        try {
            new SoftwareEngineer("SE09", "Ten", 1000, "Python", -5);
        } catch (IllegalArgumentException ex) {
            System.out.println("Loi: " + ex.getMessage());
        }

        try {
            e1.increaseSalary(0);
        } catch (IllegalArgumentException ex) {
            System.out.println("Loi: " + ex.getMessage());
        }

        try {
            e1.increaseSalary(-5, true);
        } catch (IllegalArgumentException ex) {
            System.out.println("Loi: " + ex.getMessage());
        }

        Employee sameId = new Employee("E01", "Nguoi khac trung ma");
        System.out.println("Them nguoi trung ma E01: " + team.addMember(sameId));
        System.out.println("Xoa ma khong ton tai: " + team.removeMember("E99"));
        System.out.println("Them null: " + team.addMember(null));

        ProjectTeam empty = new ProjectTeam("P03", "Nhom rong");
        System.out.printf("Tong chi phi nhom rong: %.0f%n", empty.calculateTotalMonthlyCost());
        empty.close();
    }
}
