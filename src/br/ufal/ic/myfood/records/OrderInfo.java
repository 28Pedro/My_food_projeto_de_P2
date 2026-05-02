package br.ufal.ic.myfood.records;

import java.util.List;

public record OrderInfo(String client,
                        String enterpriseId,
                        String orderId,
                        List<String> productList
                        ) {}
