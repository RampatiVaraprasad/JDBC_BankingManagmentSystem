public class Customer {

    private int customerId;
    private String customerName;
    private String email;
    private String phone;

    // Default constructor
    public Customer() {
    }

    // Used while creating a new customer
    public Customer(String customerName, String email, String phone) {
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
    }

    // Used when all database values are available
    public Customer(int customerId, String customerName, String email, String phone) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}