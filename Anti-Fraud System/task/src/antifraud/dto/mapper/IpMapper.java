package antifraud.dto.mapper;

import antifraud.dto.IpResponse;
import antifraud.model.Ip;
import org.springframework.stereotype.Component;

@Component
public class IpMapper {
    public IpResponse toIpResponse(Ip ip){
        return new IpResponse().setId(ip.getId()).setIp(ip.getIp());
    }
}
