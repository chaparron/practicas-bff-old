package bff.bridge.http

import bff.bridge.RecommendedOrderBridge
import bff.configuration.AccessToBackendDeniedException
import bff.model.FavouriteProductInput
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestOperations
import org.springframework.web.util.UriComponentsBuilder

import static org.springframework.http.HttpHeaders.AUTHORIZATION

@Slf4j
class RecommendedOrderBridgeImpl implements RecommendedOrderBridge{

    RestOperations http
    URI root

    @Value('${recommended.order.url}')
    URI apiGatewayUrl


    @Override
    Boolean setFavouriteProduct(FavouriteProductInput favoriteProductInput) {
        URI uri = UriComponentsBuilder.fromUri(apiGatewayUrl.resolve("favoriteproducts/${favoriteProductInput.productId}")).toUriString().toURI()

        try{
            def response = http.exchange(
                RequestEntity.method(HttpMethod.PUT, uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, "Bearer ${favoriteProductInput.accessToken}")
                        .build()
                , Boolean)
            response.statusCode == HttpStatus.OK
        }catch (AccessToBackendDeniedException accessToBackendDeniedException) {
            throw accessToBackendDeniedException
        }catch(Exception e) {
            Boolean.FALSE
        }
    }

    @Override
    Boolean unsetFavouriteProduct(FavouriteProductInput favoriteProductInput) {
        URI uri = UriComponentsBuilder.fromUri(apiGatewayUrl.resolve("favoriteproducts/${favoriteProductInput.productId}")).toUriString().toURI()

        try{
            def response = http.exchange(
                    RequestEntity.method(HttpMethod.DELETE, uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(AUTHORIZATION, "Bearer ${favoriteProductInput.accessToken}")
                            .build()
                    , Boolean)
            response.statusCode == HttpStatus.OK
        }catch (AccessToBackendDeniedException accessToBackendDeniedException) {
            throw accessToBackendDeniedException
        }catch(Exception e) {
            Boolean.FALSE
        }
    }

}