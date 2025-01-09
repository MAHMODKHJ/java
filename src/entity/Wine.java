package entity;

public class Wine {
    private String catalogNumber;
    private String name;
    private String description;
    private int productionYear;
    private int price; 
    private String sweetnessLevel;
    private String image;
    private int manufacturerId;

    public Wine(String catalogNumber, String name, String description, int productionYear,
                int price, String sweetnessLevel, String image, int manufacturerId) {
        this.catalogNumber = catalogNumber;
        this.name = name;
        this.description = description;
        this.productionYear = productionYear;
        this.price = price; 
        this.sweetnessLevel = sweetnessLevel;
        this.image = image;
        this.manufacturerId = manufacturerId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getPrice() { 
        return price;
    }

    public void setPrice(int price) { 
        this.price = price;
    }

    public String getSweetnessLevel() {
        return sweetnessLevel;
    }

    public void setSweetnessLevel(String sweetnessLevel) {
        this.sweetnessLevel = sweetnessLevel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
}
