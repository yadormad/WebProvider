package server.model.service.impl;

import entity.impl.Service;
import entity.impl.ServiceType;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.main.ServerProperties;
import server.model.service.ServiceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class ServiceManagerTxt implements ServiceManager {

    private File servicesFile;
    private ArrayList<Service> services;
    private DateFormat dateFormat;

    public ServiceManagerTxt(String serviceFilePath) {
        servicesFile = new File(serviceFilePath);
        services = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat(ServerProperties.getInstance().getDateFormat());
    }

    @Override
    public Collection<Service> getAllServices() throws DbAccessException, DateFormatException, WrongServiceTypeException {
        Scanner serviceScanner;
        try {
            serviceScanner = new Scanner(servicesFile);
        } catch (FileNotFoundException e) {
            throw new DbAccessException("Service file access denied");
        }
        while(serviceScanner.hasNextLine() && serviceScanner.hasNext()){
            Service nextService;
            int id, clientId;
            String name, stringType;
            ServiceType type;
            Date startDate, endDate;
            try {
                id = serviceScanner.nextInt();
                clientId = serviceScanner.nextInt();
                name = serviceScanner.next();
                stringType = serviceScanner.next();
                if (stringType.equalsIgnoreCase("tv")) {
                    type = ServiceType.TV;
                } else if (stringType.equalsIgnoreCase("phone")) {
                    type = ServiceType.PHONE;
                } else if (stringType.equalsIgnoreCase("internet")) {
                    type = ServiceType.INTERNET;
                } else {
                    throw new WrongServiceTypeException("No such service type in " + services.size() + "line");
                }
                startDate = dateFormat.parse(serviceScanner.next());
                endDate = dateFormat.parse(serviceScanner.next());
                nextService = new Service(id, clientId, name, type, startDate, endDate);
                services.add(nextService);
            } catch (ParseException e) {
                throw new DateFormatException("Wrong data format in " + (services.size() + 1) + "-line in service file.\n" + "Correct date format - " + dateFormat.toString());
            }
        }
        serviceScanner.close();
        return services;
    }

    @Override
    public void addService(Service newService) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteService(int serviceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateService(Service updService) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void commit() throws DbAccessException {
        FileWriter serviceWriter;
        try {
            serviceWriter = new FileWriter(servicesFile, false);
            for(Service nextService:services){
                String serviceString = String.valueOf(nextService.getId()) + ' ' +
                        nextService.getClientId() + ' ' +
                        nextService.getName() + ' ' +
                        nextService.getType() + ' ' +
                        dateFormat.format(nextService.getServiceProvisionDate()) + ' ' +
                        dateFormat.format(nextService.getServiceDisablingDate()) + '\n';
                serviceWriter.write(serviceString);
            }
            serviceWriter.flush();
            serviceWriter.close();
        } catch (IOException e) {
            throw new DbAccessException("Service file access denied");
        }
    }

    @Override
    public Collection<Service> getClientServices(int clientId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getServiceClientId(int serviceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Service getServiceById(int id) {
        throw new UnsupportedOperationException();
    }
}
