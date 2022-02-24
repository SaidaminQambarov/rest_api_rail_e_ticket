package com.example.rail_e_ticket_api.service;

import com.example.rail_e_ticket_api.exception.CustomException;
import com.example.rail_e_ticket_api.response.ApiResponse;
import com.example.rail_e_ticket_api.service.base.BaseService;
import com.example.rail_e_ticket_api.payload.CarDto;
import com.example.rail_e_ticket_api.entity.Car;
import com.example.rail_e_ticket_api.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.rail_e_ticket_api.util.interfaces.ResponseConstants.NOT_FOUND;
import static com.example.rail_e_ticket_api.util.interfaces.ResponseConstants.SUCCESS;

@RequiredArgsConstructor
@Service
public class CarService implements BaseService<CarDto> {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse add(CarDto carDto) {
        checkCar(carDto.getCode());
        Car car = modelMapper.map(carDto, Car.class);
        carRepository.save(car);
        return new ApiResponse(SUCCESS, 200, car);
    }

    @Override
    public ApiResponse getById(UUID id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent())
            return new ApiResponse(SUCCESS, 200, carOptional.get());
        throw new CustomException(NOT_FOUND);
    }

    @Override
    public ApiResponse getList() {
        List<Car> carList = carRepository.findAll();
        return new ApiResponse(SUCCESS, 200, carList);
    }

    @Override
    public ApiResponse updateById(UUID id, CarDto carDto) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = modelMapper.map(carDto, Car.class);
            car.setId(id);
            carRepository.save(car);
            return new ApiResponse(SUCCESS, 200, car);
        }
        throw new CustomException(NOT_FOUND);
    }

    @Override
    public ApiResponse deleteById(UUID id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()){
            carRepository.deleteById(optionalCar.get().getId());
            return new ApiResponse(SUCCESS, 200, optionalCar.get());
        }
        throw new CustomException(NOT_FOUND);
    }

    private void checkCar(String code) {
        Optional<Car> carOptional = carRepository.findByCode(code);

        if (carOptional.isPresent())
            throw new CustomException("Car with this " + code + " code is already exist");
    }
}
