package bff.model

import groovy.transform.Immutable

@Immutable
class MoneyInput {
    String currency
    Double amount
}
