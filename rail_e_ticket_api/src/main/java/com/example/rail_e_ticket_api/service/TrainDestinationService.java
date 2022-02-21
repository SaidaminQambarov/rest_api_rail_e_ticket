package com.example.rail_e_ticket_api.service;

import com.example.rail_e_ticket_api.dto.TrainDestinationDto;
import com.example.rail_e_ticket_api.entity.TrainDestination;
import com.example.rail_e_ticket_api.exception.CustomException;
import com.example.rail_e_ticket_api.repository.TrainDestinationRepository;
import com.example.rail_e_ticket_api.response.ApiResponse;
import com.example.rail_e_ticket_api.service.base.BaseService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.rail_e_ticket_api.constants.ResponseConstants.*;

@Service
@RequiredArgsConstructor
public class TrainDestinationService implements BaseService<TrainDestinationDto> {

    private final TrainDestinationRepository trainDestinationRepository;
    private final ModelMapper mapper;

    @Override
    public ApiResponse add(TrainDestinationDto trainDestinationDto) {
        checkTrainDestination(trainDestinationDto.getDepartureDate(), trainDestinationDto.getArriveDate(), trainDestinationDto.getPrice().getId());
        TrainDestination trainDestination = mapper.map(trainDestinationDto, TrainDestination.class);
        TrainDestination trainDestination1 = trainDestinationRepository.save(trainDestination);
        return new ApiResponse(SUCCESS, 200, trainDestination1);
    }

    @Override
    public ApiResponse getById(Long id) {
        Optional<TrainDestination> trainDestination = trainDestinationRepository.findById(id);
        if (trainDestination.isPresent()){
            return new ApiResponse(SUCCESS, 200, trainDestination.get());
        }
        throw new CustomException(NOT_FOUND);
    }

    @Override
    public ApiResponse getList() {
        List<TrainDestination> trainDestinationList = trainDestinationRepository.findAll();
        return new ApiResponse(SUCCESS, 200, trainDestinationList);
    }

    @Override
    public ApiResponse updateById(Long id, TrainDestinationDto trainDestinationDto) {
        Optional<TrainDestination> trainDestination = trainDestinationRepository.findById(id);
        if (trainDestination.isPresent()){
            TrainDestination trainDestination1 = mapper.map(trainDestinationDto, TrainDestination.class);
            trainDestination1.setId(id);
            return new ApiResponse(SUCCESS, 200, trainDestination1);
        }
        throw new CustomException(NOT_FOUND);
    }

    @Override
    public ApiResponse deleteById(Long id) {
        Optional<TrainDestination> trainDestinationRepositoryById = trainDestinationRepository.findById(id);
        if (trainDestinationRepositoryById.isPresent()){
            trainDestinationRepository.delete(trainDestinationRepositoryById.get());
            return new ApiResponse(SUCCESS, 200, trainDestinationRepositoryById.get());
        }
        throw new CustomException(NOT_FOUND);
    }

    protected void checkTrainDestination(Date departureDate, Date arriveDate, Long priceId){
        Optional<TrainDestination> byDepartureDateAndArriveDateAndPriceId =
                trainDestinationRepository.findByDepartureDateAndArriveDateAndPriceId(departureDate, arriveDate, priceId);
        if (byDepartureDateAndArriveDateAndPriceId.isPresent())
            throw new CustomException("Train is already is added this time: " + departureDate);
    }
}
