package bff

import bff.configuration.BridgeRestTemplateResponseErrorHandler
import bff.model.*
import com.coxautodev.graphql.tools.SchemaParserDictionary
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestOperations

@SpringBootApplication(exclude = [SecurityAutoConfiguration, ManagementWebSecurityAutoConfiguration])
class Main {
    static void main(String[] args) {
        SpringApplication.run(Main, args)
    }

    @Bean
    SchemaParserDictionary schemaParserDictionary() {
        new SchemaParserDictionary()
                .add(GenericCredentials.class)
                .add(UpgradeRequired.class)
                .add(ProfileCredentials.class)
                .add(RefreshCredentials.class)
                .add(PreSignUpFailed.class)
                .add(UsernameRegistrationFailed.class)
                .add(ResetPasswordDemandFailed.class)
                .add(ConfirmPasswordFailed.class)
                .add(ChangePasswordFailed.class)
                .add(CustomerErrorFailed.class)
                .add(ProductFailed.class)
                .add(RegisterFailed.class)
                .add(LoginFailed.class)
                .add(SignedChallengeDemandFailed.class)
                .add(TooManyShipments.class)
                .add(TooManyRequests.class)
                .add(ChallengeDemandFailed.class)
                .add(ChallengeAnswerFailed.class)
                .add(Challenge.class)
                .add(OrderUpdateFailed.class)
                .add(CustomerOrdersResponse.class)
                .add(Product.class)
                .add(Prices.class)
                .add(Cart.class)
                .add(CartFailed.class)
                .add(PlaceOrderFailed.class)
                .add(AddressFailed.class)
                .add(AddAddressFailed.class)
                .add(DeleteAddressFailed.class)
                .add(CustomerOrderFindFailed.class)
                .add(SignInFailed.class)
                .add(PasswordlessSignUpFailed.class)
                .add(CustomerRateSupplierFailed.class)
                .add(CustomerReportRateFailed.class)
                .add(SupplierFailed.class)
                .add(SupplierOrderFailed.class)
                .add(GetHomeBrandsFailed.class)
                .add(GetHomeBrandsResult.class)
                .add(UploadDocumentFailed.class)
                .add(UploadedDocument.class)
                .add(OrderSummaryResponse.class)
                .add(SummaryFailed.class)
                .add(FinalOrderState.class)
                .add(Promotion.class)
                .add(GetLandingPromotionFailed.class)
                .add(CustomerOrderResponse.class)
                .add(CustomerSupplierOrdersResponse.class)
                .add(SearchFailed.class)
                .add(SearchResult.class)
                .add(BannerDialog.class)
                .add(SiteConfigurationFailed.class)
                .add(BannerDialogResult.class)
                .add(PreviewSearchResult.class)
                .add(RootCategoriesResult.class)
                .add(RootCategoriesFailed.class)
                .add(PromotionFailed.class)
                .add(ValidateOrderResponse.class)
                .add(ValidateOrderResponseV1.class)
                .add(ValidateOrderFailed.class)
                .add(PreviewHomeSupplierResponse.class)
                .add(PreviewHomeSupplierFailed.class)
                .add(LegacyCredentials.class)
                .add(AdBanner.class)
                .add(PreviewProductSearch.class)
                .add(ProductSearch.class)
                .add(Brand.class)
                .add(PreviewSupplier.class)
                .add(Discount.class)
                .add(FreeProduct.class)
                .add(PhoneStatus.class)
                .add(AddBranchOfficeFailed.class)
                .add(RequestForExternalPayment.class)
                .add(RequestForExternalPaymentFailed.class)
                .add(CreditLines.class)
                .add(SuperMoneyCreditLine.class)
                .add(ButtonWithUrlCreditLinesAction.class)
                .add(LoanPayment.class)
                .add(Invoice.class)
                .add(Loan.class)
                .add(LoanPaymentFailed.class)
                .add(InvoicesResponse.class)
                .add(InvoiceRetailerResponse.class)
                .add(FixedQuantityFreeProduct.class)
                .add(MultipliedQuantityFreeProduct.class)
                .add(JpMorganCreateDigitalPayment.class)
                .add(CreateDigitalPaymentFailed.class)
                .add(DigitalPayment.class)
                .add(DigitalPaymentFailed.class)
                .add(SimpleTextButton.class)
                .add(JPMorganMainPaymentProvider.class)
                .add(JPMorganUPIPaymentProvider.class)
                .add(SupermoneyPaymentProvider.class)
                .add(DigitalPaymentPaymentData.class)
                .add(BuyNowPayLaterPaymentData.class)
                .add(NetBanking.class)
                .add(UPI.class)
                .add(CreditCard.class)
                .add(DebitCard.class)
                .add(DigitalWallet.class)
                .add(BuyNowPayLaterPaymentMethod.class)
    }

    /**
     *
     * @return restOperations with custom error handler
     */
    @Bean
    RestOperations restOperations() {
        new RestTemplateBuilder()
                .errorHandler(new BridgeRestTemplateResponseErrorHandler())
                .build()
    }
}


