package com.awb.automarket.controller;

import com.awb.automarket.customvalidation.CustomValidator;
import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.advertDto.AdvertDTO;
import com.awb.automarket.dto.advertDto.AdvertFilter;
import com.awb.automarket.dto.advertDto.CreateAdvertRequest;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.entity.GearboxType;
import com.awb.automarket.filter.SearchCriteria;
import com.awb.automarket.security.CustomUserDetails;
import com.awb.automarket.services.IAdvertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequestMapping("api/v1/advert")
@RestController
public class AdvertController {

    Logger logger =  LoggerFactory.getLogger(AdvertController.class);
    
    @Autowired
    IAdvertService advertService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping(path = "/image/{filePath}")
    public void getImage(HttpServletResponse response, @PathVariable String filePath) throws IOException {
        File file = new File("C:\\Users\\Stefan\\IdeaProjects\\AutoMarket\\resources\\" + filePath);
        if(file.exists()) {
            String contentType = "application/octet-stream";
            response.setContentType(contentType);
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            // copy from in to out
            IOUtils.copy(in, out);
            out.close();
            in.close();
        }else {
            response.setStatus(404);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity CreateAdvert(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("files") List<MultipartFile> files,
                                       @RequestParam("JsonObject") String data){

        try{
            ObjectMapper mapper = new ObjectMapper();
            CreateAdvertRequest createAdvertRequest = mapper.readValue(data, CreateAdvertRequest.class);
            ServiceResponseModel validationResult = CustomValidator.ValidateObject(createAdvertRequest);

            if(validationResult != null) return validationResult.toResponseEntity(logger);
            List<String> filesUploaded = new ArrayList<>();
            if (!files.isEmpty()) {
                for(int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    try {
                        String uploadsDir = "/resources";
                        String realPathtoUploads = "C:\\Users\\Stefan\\IdeaProjects\\AutoMarket\\resources\\";//request.getServletContext().getRealPath(uploadsDir);
                        if (!new File(realPathtoUploads).exists()) {
                            new File(realPathtoUploads).mkdir();
                        }

                        String orgName = (new Date()).getTime() + "_" + file.getOriginalFilename();
                        filesUploaded.add(orgName);
                        String filePath = realPathtoUploads + orgName;
                        File dest = new File(filePath);
                        file.transferTo(dest);
                    } catch (Exception ex) {

                    }
                }
            }
            createAdvertRequest.setUser_id(userDetails.getId());
            return advertService.save(createAdvertRequest, filesUploaded).toResponseEntity(logger);
        }
        catch (JsonProcessingException ex){
            return ServiceResponseModel.bad(ErrorResponse.UnknownError()).toResponseEntity(logger);
        }
        catch (Exception ex) {
            return ServiceResponseModel.bad(ErrorResponse.UnknownError()).toResponseEntity(logger);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity UpdateAdvert(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CreateAdvertRequest createAdvertRequest){
        ServiceResponseModel validationResult = CustomValidator.ValidateObject(createAdvertRequest);

        if(validationResult != null) return validationResult.toResponseEntity(logger);

        ServiceResponseModel<AdvertDTO> extists = advertService.findById(createAdvertRequest.getAdvert_id());
        if(extists.hasError()) return extists.toResponseEntity(logger);

        if(extists.getResponseData().getUser_id() != userDetails.getId()) return ServiceResponseModel.bad(ErrorResponse.InvalidCredentials()).toResponseEntity(logger);

        return advertService.update(createAdvertRequest).toResponseEntity(logger);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity DeleteAdvert(@PathVariable("id")Integer id){
        return advertService.deleteById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity GetById(@PathVariable("id")Integer id){
        return advertService.findById(id).toResponseEntity(logger);
    }

    @GetMapping(path = "/all/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity GetAll(@PathVariable("userId") Integer userId){
        return advertService.findAllUserAdverts(userId).toResponseEntity(logger);
    }

    @GetMapping(path = "/admin/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity GetAll(@RequestParam Integer page, @RequestParam String sort){
        return advertService.findAll(page, sort).toResponseEntity(logger);
    }


    @GetMapping(path = "/all")
    public ResponseEntity GetAllAdverts(@RequestParam String filter, @RequestParam Integer page) throws JsonProcessingException {
        try{
            List<SearchCriteria> params = new ArrayList<SearchCriteria>();
            AdvertFilter advertFilter;
            if(filter.isEmpty() == false) {
                ObjectMapper mapper = new ObjectMapper();
                advertFilter = mapper.readValue(new String(Base64.getDecoder().decode(filter)), AdvertFilter.class);

                if (advertFilter.kmMin != -1) {
                    params.add(new SearchCriteria("km", ">", advertFilter.kmMin));
                }
                if (advertFilter.kmMax != -1) {
                    params.add(new SearchCriteria("km", "<", advertFilter.kmMax));
                }
                if (advertFilter.yearMin != -1) {
                    params.add(new SearchCriteria("year", ">", advertFilter.yearMin));
                }
                if (advertFilter.yearMax != -1) {
                    params.add(new SearchCriteria("year", "<", advertFilter.yearMax));
                }
                if (advertFilter.priceMin != -1) {
                    params.add(new SearchCriteria("price", ">", advertFilter.priceMin));
                }
                if (advertFilter.priceMax != -1) {
                    params.add(new SearchCriteria("price", "<", advertFilter.priceMax));
                }
                if (advertFilter.horsePowerMin != -1) {
                    params.add(new SearchCriteria("horsePower", ">", advertFilter.horsePowerMin));
                }
                if (advertFilter.horsePowerMax != -1) {
                    params.add(new SearchCriteria("horsePower", "<", advertFilter.horsePowerMax));
                }
                if (advertFilter.gearbox != GearboxType.EMPTY) {
                    params.add(new SearchCriteria("gearboxType", ":", advertFilter.gearbox));
                }
            }
            else{
                advertFilter = new AdvertFilter();
                advertFilter.setBrand(-1);
                advertFilter.setModel(-1);
                advertFilter.setBs(-1);
            }
            ServiceResponseModel resp =  advertService.filterResults(params, advertFilter, page);
            return resp.toResponseEntity(logger);
        }
        catch (Exception ex){
            return ServiceResponseModel.bad(ErrorResponse.UnknownError()).toResponseEntity(logger);
        }
    }

    @GetMapping(path = "/all/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity GetAllUserAdverts(@AuthenticationPrincipal CustomUserDetails userDetails){
        return advertService.findAllUserAdverts(userDetails.getId()).toResponseEntity(logger);
    }

    @GetMapping(path = "/empty")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CreateAdvertRequest Empty(){
        return new CreateAdvertRequest();
    }
}
