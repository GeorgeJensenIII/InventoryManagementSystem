import java.util.ArrayList;

/**
 * Created by gwjense on 6/18/17.
 */
public class Inventory {
    private ArrayList<Product> products;

    public Inventory(){
        products = new ArrayList<Product>();
    }
    public void addProduct(Product product)
    {
        products.add(product);
    }

    public void removeProduct(int productID)
    {
        products.remove(lookupProduct(productID));
    }

    public Product lookupProduct(int productID)
    {
        Product result = null;
        for (Product p : products)
        {
            if (productID == p.getProductID())
            {
                result = p;
            }
        }
        return result;
    }

    public void updateProduct(int productId, Product product) {
        Product existingProduct = lookupProduct(productId);
        existingProduct.setPrice(product.getPrice());
        existingProduct.setName(product.getName());
        existingProduct.getParts().clear();
        existingProduct.getParts().addAll(product.getParts());
        existingProduct.setMin(product.getMin());
        existingProduct.setMax(product.getMax());
        existingProduct.setInstock(product.getInstock());
    }

    public ArrayList<Product> getProducts()
    {
        return products;
    }

}

