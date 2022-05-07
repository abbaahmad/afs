package antifraud.service;

import antifraud.controller.request.IpRequest;
import antifraud.dto.IpResponse;
import antifraud.dto.StatusResponse;
import antifraud.dto.mapper.IpMapper;
import antifraud.model.Ip;
import antifraud.repository.IpRepository;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IpService {
    @Autowired
    IpRepository ipRepository;

    @Autowired
    IpMapper mapper;

    public IpResponse saveSuspiciousIp(IpRequest request) {

        if(!InetAddressValidator.getInstance().isValidInet4Address(request.getIp()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if(!ipRepository.existsByIp(request.getIp()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        Ip savedIp = ipRepository.save(new Ip().setIp(request.getIp()));

        return mapper.toIpResponse(savedIp);
    }

    public StatusResponse delete(String ip) {
        if(!InetAddressValidator.getInstance().isValidInet4Address(ip))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Ip ipFromDb = ipRepository.findByIp(ip)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ipRepository.delete(ipFromDb);
        return new StatusResponse().setStatus("IP " + ip + " successfully removed!");
    }

    public List<IpResponse> getAll() {
        return ipRepository.findAll()
                .stream()
                .map(mapper::toIpResponse)
                .collect(Collectors.toUnmodifiableList());
    }
}
