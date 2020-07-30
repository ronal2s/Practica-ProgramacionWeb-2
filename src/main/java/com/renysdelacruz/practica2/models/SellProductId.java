package com.renysdelacruz.practica2.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SellProductId implements Serializable {
    @Column(name = "sale_id")
    private long saleId;

    @Column(name = "product_id")
    private int productId;

    protected SellProductId() {}

    public SellProductId(long ventaId, int productoId) {
        this.saleId = ventaId;
        this.productId = productoId;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long ventaId) {
        this.saleId = ventaId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productoId) {
        this.productId = productoId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        SellProductId that = (SellProductId) o;
        return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, productId);
    }
}
