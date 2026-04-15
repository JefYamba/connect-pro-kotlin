package dev.jefy.connectpro.portfolio.applicaion.dtos;


import dev.jefy.connectpro.portfolio.domain.vo.LocationInfo;

/**
 * @author Jôph Yamba
 */
public record LocationInfoData(
        String country,
        String city,
        String address,
        Double latitude,
        Double longitude
){
    public static LocationInfoData from(LocationInfo locationInfo) {
        return new LocationInfoData(
                locationInfo.country(), 
                locationInfo.city(), 
                locationInfo.address(), 
                locationInfo.latitude(), 
                locationInfo.longitude()
        );
    }

    public LocationInfo toDomain(){
        return new LocationInfo(
                this.country,
                this.city,
                this.address,
                this.latitude,
                this.longitude
        );
    }
}
