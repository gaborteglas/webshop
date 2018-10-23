package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.dbTables.Product;

import java.text.Normalizer;

public class ProductAddressGenerator {


    public static String generateUserFriendlyAddress(Product product) {
        String address = product.getName().trim().replaceAll(" ", "-").toLowerCase();
        return Normalizer.normalize(address, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
