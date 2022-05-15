package com.company.JSONModels.search.input;

import com.google.gson.annotations.SerializedName;

public class Criteria {
    private String lastName;
    private String productName;
    private String minTimes;
    private String minExpenses;
    private String maxExpenses;
    private String badCustomers;
    private String randomString;

    public String getLastName() {
        return lastName;
    }
    public String getProductName() {
        return productName;
    }
    public String getMinTimes() {
        return minTimes;
    }
    public String getMinExpenses() {
        return minExpenses;
    }
    public String getMaxExpenses() {
        return maxExpenses;
    }
    public String getBadCustomers() {
        return badCustomers;
    }

    /**
     * Маппинг критериев:
     * 1.	Фамилия — поиск покупателей с этой фамилией
     * 2.	Название товара и число раз — поиск покупателей, купивших этот товар не менее, чем указанное число раз
     * 3.	Минимальная и максимальная стоимость всех покупок — поиск покупателей, у которых общая стоимость всех покупок за всё время попадает в интервал
     * 4.	Число пассивных покупателей — поиск покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
     * */
    public int getCriteriaType() {
        if (lastName != null) {
            return 1;
        }
        else if (productName != null && minTimes != null) {
            return 2;
        }
        else if (minExpenses != null && maxExpenses !=null) {
            return  3;
        }
        else if (badCustomers != null) {
            return 4;
        }
        else return 0;
    }
}
