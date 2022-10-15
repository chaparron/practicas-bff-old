package bff.model

import bff.model.order.OrderInputV2
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.InheritConstructors

import static java.util.Optional.of

interface PlaceOrderResult {}

interface CartResult {}

interface CustomerRateSupplierResult {}

interface CustomerReportRateResult {}

interface SummaryResult {}

@InheritConstructors
class OrderUpdateFailed extends RuntimeException implements OrderUpdateResult {
    OrderUpdateReason reason
}

enum OrderUpdateReason {
    INVALID_SUPPLIER_ORDERS_STATUS,
    ORDER_NOT_FOUND,
    INVALID_SUPPLIER,
    INVALID_DELIVERY_COST,
    INVALID_PRODUCTS,
    CANCEL_REQUESTED_SUPPLIER_MUST_CANCEL

    def build() {
        new OrderUpdateFailed(reason: this)
    }
}

enum SupplierOrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    CANCELED
}

enum FilterOrderStatus {
    ALL,
    OPEN,
    CLOSED
}

enum OrderStatus {
    PENDING,
    IN_PROGRESS,
    FINISHED
}

enum RatingOwner {
    SUPPLIER,
    CUSTOMER
}


enum RatingStatus {
    ENABLED,
    DISABLED,
    REPORTED
}

enum UserType {
    SUPPLIER,
    CUSTOMER
}

enum SupplierCancelOptionReason {
    INCORRECT_PRICE,
    INCORRECT_TAX,
    CUSTOMER_CLOSED,
    INSUFFICIENT_AMOUNT,
    CUSTOMER_REGRET,
    NO_STOCK,
    STORE_OUTSIDE_COVERAGE_AREA,
    OTHERS,
    ACCEPT_CANCELED_ORDER
}

enum CustomerCancelOptionReason {
    REGRET,
    COMMODITY_DELAY,
    PRICE_DIFFERENCE,
    DELIVERY_DIFFERENCE,
    NO_MONEY,
    OTHER
}

interface OrderUpdateResult {}

interface CustomerOrdersResult {}

interface CustomerOrderResult {}

interface CustomerSupplierOrderResult {}

interface CustomerLegalDocumentResult {}

class CancelOrderInput {
    String accessToken
    Integer orderId
    Integer supplierOrderId
    String comment
    CustomerCancelOptionReason cancelOptionReason
}


class FinalOrderState implements OrderUpdateResult {
    Long orderId
    Long supplierOrderId
    Boolean cancelRequested
    UserType canceledBy
    CustomerCancelOptionReason customerCancellationReason
    SupplierCancelOptionReason supplierCancelOptionReason
    OrderStatus orderStatus
}

class OrderCancellation {
    Long supplierOrderId
    Long orderId
    Long supplierId
    UserType userType
    CustomerCancelOptionReason customerCancelOptionReason
    SupplierCancelOptionReason supplierCancelOptionReason
}

class SortResult {
    String field
    Direction direction

    enum Direction {
        ASC, DESC
    }
}


class PaginatedInput {
    Long page
    Long size
    SortResult sort = new SortResult()
}

class FindOrdersInput extends PaginatedInput {
    String accessToken
    Integer orderId
    String countryId
    FilterOrderStatus status
    MillisecondsPeriodInput period
}

class FindCustomerAndSupplierOrdersInput {
    String accessToken
    Integer orderId
}

class FindCustomerLegalDocumentInput {
    String accessToken
    String documentId
}

class FindSupplierOrderInput {
    String accessToken
    String countryId
    Integer orderId
    Integer supplierOrderId
}

class GetSupplierRatingsInput extends PaginatedInput {
    String accessToken
    Long supplierId
}

class GetSupplierOrdersInput {
    String accessToken
    Long orderId
}

class CustomerOrdersResponse extends PaginatedResponse<Order> implements CustomerOrdersResult {
}

class CustomerOrderResponse implements CustomerOrderResult {
    String accessToken
    SupplierOrderResult supplierOrder
    Customer customer
    OrderCancellation orderCancellation
}

class CustomerSupplierOrdersResponse implements CustomerSupplierOrderResult {
    String accessToken
    List<SupplierOrderAndOrderCancellations> supplierOrderAndOrderCancellations
    Customer customer
}


class PreSignedObject {
    String url
    Long expiresAt
    String id
}

class SupplierRatingsResponse extends PaginatedResponse<Rating> {

}

class Headers {
    Long page
    Long page_size
    Long total
    SortResult sort
}

class PaginatedResponse<T> {
    Headers headers
    List<T> content
}

class Order {
    String accessToken
    Long id
    OrderStatus status
    TimestampOutput created
    TimestampOutput updated
    WorkingDays workingDays
    BigDecimal total_credits
    Money totalCreditsMoney
    BigDecimal total_money
    Money totalMoneyMoney
    BigDecimal total_wabipay
    Money totalWabipayMoney
    BigDecimal discounts
    Money discountsMoney
    BigDecimal total_service_fee
    Money totalServiceFeeMoney
    BigDecimal total_pending
    Money totalPendingMoney
    BigDecimal total_discounts_used
    Money totalDiscountsUsedMoney
    BigDecimal total
    Money totalMoney
    BigDecimal subTotal
    Money subTotalMoney
    Customer customer
    List<SupplierOrder> supplierOrders
}

class SupplierOrder implements SupplierOrderResponse {
    String accessToken
    Long id
    Order order
    SupplierOrderStatus status
    SupplierOrderPaymentV2 payment
    TimestampOutput created
    TimestampOutput updated
    TimestampOutput shippedAt
    TimestampOutput shipAt
    BigDecimal deliveryCost
    Money deliveryCostMoney
    BigDecimal total
    Money totalMoney
    BigDecimal subTotal
    Money subTotalMoney
    BigDecimal credits_paid
    Money creditsPaidMoney
    BigDecimal money_paid
    Money moneyPaidMoney
    BigDecimal service_fee
    Money serviceFeeMoney
    BigDecimal total_wabipay
    Money totalWabipayMoney
    BigDecimal payment_pending
    Money paymentPendingMoney
    BigDecimal discounts
    Money discountsMoney
    BigDecimal discount_used
    Money discountUsedMoney
    Integer units
    Boolean canCustomerRate
    Boolean canSupplierRate
    Boolean customerRated
    Boolean supplierRated
    Boolean cancelRequested
    Boolean availabilityDifference
    RatingEntry rating
    HashMap<RatingOwner, Rating> ratings
    Map metadata
    List<Summary> summary
    List<AppliedPromotionResponse> appliedPromotions

    Boolean isPayable() {
        this.status == SupplierOrderStatus.CONFIRMED
    }
}

@EqualsAndHashCode
class SupplierOrderPaymentV2 {
    Long supplierOrderId
    Long paymentId
    PaymentData paymentData
}

class SupplierOrderResult {
    String accessToken
    Order order
    Long id
    SupplierOrderStatus status
    Supplier supplier
    TimestampOutput created
    TimestampOutput updated
    TimestampOutput shippedAt
    TimestampOutput shipAt
    BigDecimal deliveryCost
    Money deliveryCostMoney
    BigDecimal total
    Money totalMoney
    BigDecimal subTotal
    Money subTotalMoney
    BigDecimal credits_paid
    Money creditsPaidMoney
    BigDecimal money_paid
    Money moneyPaidMoney
    BigDecimal service_fee
    Money serviceFeeMoney
    BigDecimal payment_pending
    Money paymentPendingMoney
    BigDecimal localTaxes
    Money localTaxesMoney
    BigDecimal amount
    Money amountMoney
    BigDecimal discounts
    Money discountsMoney
    BigDecimal discount_used
    Money discountUsedMoney
    BigDecimal total_wabipay
    Money totalWabipayMoney
    Integer units
    Boolean canCustomerRate
    Boolean canSupplierRate
    Boolean customerRated
    Boolean supplierRated
    Boolean cancelRequested
    Boolean availabilityDifference
    RatingEntry rating
    List<OrderItem> products
    HashMap<RatingOwner, Rating> ratings
    Map metadata
    List<Summary> summary
    List<AppliedPromotionResponse> appliedPromotions
    SupplierOrderPaymentV2 payment
}


// TODO: verificar Front
class SupplierOrderAndOrderCancellations {
    String accessToken
    Order order
    OrderCancellation orderCancellation
    OrderCancellation cancellationDetails
    Long id
    SupplierOrderStatus status
    Supplier supplier
    TimestampOutput created
    TimestampOutput updated
    TimestampOutput shippedAt
    TimestampOutput shipAt
    BigDecimal deliveryCost
    Money deliveryCostMoney
    BigDecimal total
    Money totalMoney
    BigDecimal subTotal
    Money subTotalMoney
    BigDecimal credits_paid
    Money creditsPaidMoney
    BigDecimal money_paid
    Money moneyPaidMoney
    BigDecimal service_fee
    Money serviceFeeMoney
    BigDecimal payment_pending
    Money paymentPendingMoney
    BigDecimal localTaxes
    Money localTaxesMoney
    BigDecimal amount
    Money amountMoney
    BigDecimal discounts
    Money discountsMoney
    BigDecimal discount_used
    Money discountUsedMoney
    BigDecimal total_wabipay
    Money totalWabipayMoney
    Integer units
    Boolean canCustomerRate
    Boolean canSupplierRate
    Boolean customerRated
    Boolean supplierRated
    Boolean cancelRequested
    RatingEntry rating
    List<OrderItem> products
    HashMap<RatingOwner, Rating> ratings
    Map metadata
    List<Summary> summary
    List<Summary> orderedSummary
    List<AppliedPromotionResponse> appliedPromotions

    OrderCancellation getOrderCancellation() {
        orderCancellation ?: cancellationDetails
    }
}


class RatingEntry {
    Rating SUPPLIER
    Rating CUSTOMER
}

class Rating {
    String accessToken
    Long id
    Supplier supplier
    Customer customer // -≥ not in schema
    String customerName
    TimestampOutput created
    String opinion
    Long score
    RatingOwner owner
    RatingStatus status
    Float percentage
}

class OrderItem {
    String accessToken
    Long id
    String productTitle
    String productEan
    BigDecimal price
    BigDecimal originalPrice
    Money priceMoney
    Money originalPriceMoney
    Integer quantity
    BigDecimal subtotal
    Money subtotalMoney
    Display display
    Product product
    PartialSummary partialSummary
}

class PartialSummary {
    Long orderItemId
    Long productDisplayId
    Integer quantity
}

enum PlaceOrderFailedReason {
    DELIVERY_COST_HAS_CHANGED,
    INVALID_CUSTOMER,
    BAD_REQUEST,
    PRODUCT_PRICES_HAS_CHANGED,
    SUPPLIER_AVAILABILITY_HAS_CHANGED,
    INVALID_SUPPLIER_ORDER,
    INVALID_ORDER,
    MIN_UNITS_NO_REACHED,
    MAX_UNITS_EXCEEDED,
    MIN_AMOUNT_NO_REACHED,
    MAX_AMOUNT_EXCEEDED,
    MAX_ALLOWED_PRODUCT_EXCEEDED,
    PRODUCTS_UNAVAILABLE,
    INVALID_COUPON,
    PRODUCT_FREE_NOT_APPLY,
    PROMOTION_APPLIED_DISABLED,
    PROMOTION_APPLIED_CHANGED,
    PROMOTION_MAX_USES_EXCEEDED

    def build() {
        new PlaceOrderFailed(reason: this)
    }
}

class CustomerOrderFindFailed implements CustomerOrdersResult, CustomerOrderResult, CustomerSupplierOrderResult {
    CustomerOrderFindFailedReason reason
}

enum CustomerOrderFindFailedReason {
    ORDER_NOT_FOUND

    def build() {
        new CustomerOrderFindFailed(reason: this)
    }
}

enum CartFailedReason {
    FORBIDDEN,
    BAD_REQUEST,
    INVALID_ADDRESS,
    EMPTY_PRODUCTS,
    PRODUCTS_UNAVAILABLE

    def build() {
        new CartFailed(reason: this)
    }

}

class Cart implements CartResult {
    List<ProductCart> products //dumb
    List<ProductCart> availableProducts
    List<Supplier> suppliers
}

class ProductCart {
    Product product
    Price price
    Integer quantity
    List<SupplierPrice> suppliers //dumb
    List<SupplierPrice> supplierPrices

    ProductCart(ProductSearch product, Price price, Integer quantity) {
        this.product = new Product(product)
        this.price = price
        this.quantity = quantity
        this.supplierPrices = product.prices.collect { new SupplierPrice(it) }.toSet().toList()
    }

}

class SupplierPrice {
    String accessToken
    Integer id
    String name
    BigDecimal price
    Money priceMoney
    Display display
    Integer minUnits
    Integer maxUnits
    String avatar
    DeliveryZone deliveryZone
    SupplierProductConfiguration configuration

    SupplierPrice() {}

    SupplierPrice(Price price) {
        this.accessToken = price.accessToken
        this.id = price.supplier.id.toInteger()
        this.name = price.supplier.name
        this.price = price.value
        this.priceMoney = price.valueMoney
        this.display = price.display
        this.minUnits = price.minUnits
        this.maxUnits = price.maxUnits
        this.avatar = price.supplier.avatar
        this.deliveryZone = price.supplier.deliveryZones?.head()
        this.configuration = price.configuration
    }

}

class CartFailed implements CartResult {
    CartFailedReason reason
}

class RefreshCartInput {
    String accessToken
    List<Integer> products
}

class SyncCartInput {
    String accessToken
    Set<ProductCartItemInput> items
}

class SyncCartResult {
    List<PromotedProductsCart> promoted
    List<ProductCart> unpromoted
}

class PromotedProductsCart {
    CommercialPromotions commercialPromotions
    List<ProductCart> products

    static Optional<PromotedProductsCart> apply(CommercialPromotionType promotion,
                                                List<ProductCart> selection) {
        of(
                new PromotedProductsCart(
                        commercialPromotions: new CommercialPromotions(promotion),
                        products: selection
                )
        ).filter { promotion.appliesTo(selection) }

    }
}

class PlaceOrderFailed implements PlaceOrderResult {
    PlaceOrderFailedReason reason
}

class ProductOrderInput extends CartItemInput {
    BigDecimal price
    BigDecimal totalPrice
}

class OrderInput {
    Integer supplierId
    Long deliveryZoneId
    BigDecimal deliveryCost
    List<ProductOrderInput> products
    List<ProductFreeInput> productsFree
}

class OrderInputV1 {
    Integer supplierId
    Long deliveryZoneId
    BigDecimal deliveryCost
    List<ProductOrderInput> products
    List<AppliedPromotionInput> appliedPromotions
}

class ProductFreeInput {
    List<TriggerCartItem> triggerCartItems
    ProductFreeItemInput product
}

class TriggerCartItem {
    Integer productId
    Integer units
}

class ProductFreeItemInput {
    Integer productId
    Integer quantity
    Integer units
}

class PlaceOrderInput {
    String accessToken
    String wabiPayAccessToken
    List<String> coupons
    List<OrderInput> orders
}

class PlaceOrderInputV1 {
    String accessToken
    String wabiPayAccessToken
    List<String> coupons
    List<OrderInputV2> orders
}

class CustomerRateSupplierInput {
    String accessToken
    Integer supplierId
    Integer supplierOrderId
    Integer score
    String opinion
}

class CustomerReportRateInput {
    String accessToken
    Integer rateId
}

class OrderSummaryInput {
    String accessToken
    String wabiPayAccessToken
    List<SupplierCartProductInput> products
    List<String> coupons
}

class SupplierCartProductInput {
    List<CartItemInput> items
    Long supplierId
    Long deliveryZoneId
}

class CartItemInput {
    Long productId
    Long units
    Integer quantity
}

class ProductCartItemInput extends CartItemInput {
    Integer supplierId
}

class SummaryFailed implements SummaryResult {
    SummaryFailedReason reason
}

enum SummaryFailedReason {
    NOT_FOUND,
    PRODUCT_AVAILABILITY_HAS_CHANGED,
    IN,
    PRODUCTS_UNAVAILABLE,
    INVALID_COUPON

    def build() {
        new SummaryFailed(reason: this)
    }
}

@EqualsAndHashCode
class SupportedPaymentProvider {
    URI avatar
    PaymentProviderType code
    String getClassName() {
        this.getClass().getSimpleName()
    }
}
@EqualsAndHashCode
class JPMorganMainPaymentProvider extends SupportedPaymentProvider {
    JPMorganMainPaymentProvider() {
        this.avatar = URI.create("")
        this.code = PaymentProviderType.PAY_NOW
    }
}
@EqualsAndHashCode
class SupermoneyPaymentProvider extends SupportedPaymentProvider {
    SupermoneyPaymentProvider() {
        this.avatar = URI.create("")
        this.code = PaymentProviderType.PAY_LATER
    }
}

@EqualsAndHashCode
class JPMorganUPIPaymentProvider extends SupportedPaymentProvider {
    JPMorganUPIPaymentProvider() {
        this.avatar = URI.create("")
        this.code = PaymentProviderType.PAY_NOW
    }
}

enum PaymentProviderType {
    PAY_NOW,
    PAY_LATER
}

class OrderSummaryResponse implements SummaryResult {
    List<OrderSummary> orderSummary
    List<OrderSummary> orderedOrderSummary
}

class OrderSummary {
    Money totalProducts
    Supplier supplier
    List<Summary> summary
}


class Summary {
    String accessToken
    CartSummaryItemType type
    BigDecimal value
    Money valueMoney
    Map meta
    List<MetaEntry> metadata
    String description
}

enum CartSummaryItemType {
    PRODUCTS_TOTAL(1, true),
    IBB(2, true),
    DELIVERY_COST(3, true),
    DISCOUNT(4, true),
    ORDER_TOTAL(5, true),
    CREDITS_USED(6, true),
    WABIMONEY_USED(7, true),
    SERVICE_FEE(8, true),
    PAYMENT_PENDING(9, true),
    MAX_AMOUNT_WABIPAY_ALLOWED(10, false),
    PROMOTION(4, true),
    SUBTOTAL(11, false),
    NET_SUBTOTAL(12, false),
    DISCOUNT_USED(13, false),
    TOTAL_BILLED(14, false)

    private int position
    private Boolean visibleToSummary

    CartSummaryItemType(int position, Boolean visibleToSummary) {
        this.position = position
        this.visibleToSummary = visibleToSummary
    }

    Boolean getVisibleToSummary() {
        this.visibleToSummary
    }

    int getPosition() {
        this.position
    }

}

class MetaEntry {
    String key
    Object value
}

enum CustomerReportRateFailedReason {
    RATE_NOT_FOUND

    def build() {
        new CustomerReportRateFailed(reason: this)
    }
}

class CustomerReportRateFailed implements CustomerReportRateResult {
    CustomerReportRateFailedReason reason
}

enum CustomerRateSupplierFailedReason {
    SUPPLIER_ALREADY_RATED_BY_CUSTOMER,
    INVALID_RATE,
    INVALID_SUPPLIER_ORDER,
    INVALID_SUPPLIER_ID,
    INVALID_SCORE

    def build() {
        new CustomerRateSupplierFailed(reason: this)
    }
}

class CustomerRateSupplierFailed implements CustomerRateSupplierResult {
    CustomerRateSupplierFailedReason reason
}


interface SupplierOrderResponse {

}

enum SupplierOrderFailedReason {
    NOT_FOUND

    def build() {
        return new SupplierOrderFailed(reason: this)
    }
}

class SupplierOrderFailed implements SupplierOrderResponse {
    SupplierOrderFailedReason reason
}

class GetSupplierOrderInput {
    String accessToken
    Long supplierOrderId
}

@Deprecated
class OrderError {
    String accessToken
    OrderErrorType error
    Long supplierId
    Long productId
    Integer units
    BigDecimal prevValue
    Money prevValueMoney
    BigDecimal actualValue
    Money actualValueMoney
}

class OrderErrorV1 {
    String accessToken
    OrderErrorType error
    Long supplierId
    Long productId
    Integer units
    BigDecimal prevValue
    Money prevValueMoney
    BigDecimal actualValue
    Money actualValueMoney
    OrderErrorPriorityType priority
}

enum OrderErrorPriorityType {
    BLOCKER,
    WARNING
}

enum OrderErrorType {
    PRODUCT_PRICES_HAS_CHANGED,
    MIN_UNITS_NO_REACHED,
    MAX_UNITS_EXCEEDED,
    MAX_ALLOWED_PRODUCT_EXCEEDED,
    MAX_AMOUNT_EXCEEDED,
    MIN_AMOUNT_NO_REACHED,
    SUPPLIER_UNAVAILABLE,
    PRODUCT_UNAVAILABLE,
    DELIVERY_COST_CHANGED,
    INVALID_DELIVERY_ZONE,
    PRODUCT_FREE_NOT_APPLY,
    PROMOTION_APPLIED_DISABLED,
    PROMOTION_APPLIED_CHANGED,
    PROMOTION_MAX_USES_EXCEEDED
}

@Deprecated
interface ValidateOrderResult {}

interface ValidateOrderResultV1 {}

@Deprecated
class ValidateOrderResponse implements ValidateOrderResult {
    List<OrderError> errors
}

class ValidateOrderFailed implements ValidateOrderResult, ValidateOrderResultV1 {
    ValidateOrderFailedReason reason
}

class ValidateOrderResponseV1 implements ValidateOrderResultV1 {
    List<OrderErrorV1> errors
}

enum ValidateOrderFailedReason {
    INVALID_ORDER,
    INVALID_PROMOTION

    def build() {
        new ValidateOrderFailed(reason: this)
    }
}

@Deprecated
class ValidateOrderInput {
    String accessToken
    List<OrderInput> orders
}

class ValidateOrderInputV1 {
    String accessToken
    List<OrderInputV1> orders
}

class MarkSuggestionInput {
    String accessToken
    List<Long> supplierIds
}

enum PaymentModeType {
    PAY_NOW,
    PAY_LATER
}

enum PaymentStatus {
    TOTALLY_PAID,
    PARTIALLY_PAID,
    UNPAID
}

@Immutable
class PaymentMode {
    PaymentModeType paymentType
}

@EqualsAndHashCode
class SimpleTextButton {
    SimpleTextButtonBehavior behavior
    String textKey

    SimpleTextButton(SimpleTextButtonBehavior behavior, String textKey) {
        this.behavior = behavior
        this.textKey = textKey
    }

    static SimpleTextButton hidden() {
        new SimpleTextButton(SimpleTextButtonBehavior.HIDDEN, "")
    }
}

enum SimpleTextButtonBehavior {
    VISIBLE, HIDDEN, DISABLE
}

interface PaymentData {}

@EqualsAndHashCode
class DigitalPaymentPaymentData implements PaymentData {
    PaymentMethod paymentMethod
}

@EqualsAndHashCode
class BuyNowPayLaterPaymentData implements PaymentData {
    PaymentMethod paymentMethod
}

interface PaymentMethod {}

@EqualsAndHashCode
class NetBanking implements PaymentMethod {}

@EqualsAndHashCode
class CreditCard implements PaymentMethod {}

@EqualsAndHashCode
class DebitCard implements PaymentMethod {}

@EqualsAndHashCode
class UPI implements PaymentMethod {}

@EqualsAndHashCode
class DigitalWallet implements PaymentMethod {}

@EqualsAndHashCode
class BuyNowPayLaterPaymentMethod implements PaymentMethod {}

@EqualsAndHashCode
class DefaultPaymentMethod implements PaymentMethod {}