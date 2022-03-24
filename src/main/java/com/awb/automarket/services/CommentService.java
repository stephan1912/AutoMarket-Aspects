package com.awb.automarket.services;

import com.awb.automarket.dto.ErrorResponse;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.commentDto.CommentDTO;
import com.awb.automarket.entity.Advert;
import com.awb.automarket.entity.Brand;
import com.awb.automarket.entity.Comment;
import com.awb.automarket.entity.User;
import com.awb.automarket.repository.AdvertRepository;
import com.awb.automarket.repository.CommentRepository;
import com.awb.automarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService{

    CommentRepository commentRepository;
    UserRepository userRepository;
    AdvertRepository advertRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, AdvertRepository advertRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }
    @Override
    public ServiceResponseModel findById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()) return  ServiceResponseModel.ok(new CommentDTO(comment.get()));
        return ServiceResponseModel.ClassNotFound(Comment.class);
    }

    @Override
    public ServiceResponseModel findAllUserComments(Integer userId) {
        Optional<List<Comment>> commentList = commentRepository.findAllUserComments(userId);

        if(!commentList.isPresent()) return ServiceResponseModel.ok(new ArrayList<CommentDTO>());

        return ServiceResponseModel.ok(commentList.get().stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList()));
    }

    @Override
    public ServiceResponseModel deleteById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if(!comment.isPresent())
            return ServiceResponseModel.ClassNotFound(Comment.class);

        Comment toUpdate = comment.get();
        toUpdate.getAdvert().removeComment(toUpdate);
        toUpdate.getUser().getCommentList().remove(toUpdate);

        commentRepository.save(toUpdate);
        commentRepository.deleteById(toUpdate.getId());
        return ServiceResponseModel.ok(null);
    }

    @Override
    public ServiceResponseModel save(CommentDTO commentDTO) {

        Optional<User> userO = userRepository.findById(commentDTO.getUser_id());
        if(userO.isPresent() == false)
            return ServiceResponseModel.ClassNotFound(User.class);

        Optional<Advert> advertO = advertRepository.findById(commentDTO.getAdvert_id());
        if(advertO.isPresent() == false)
            return ServiceResponseModel.ClassNotFound(Advert.class);

        User user = userO.get();
        Advert advert = advertO.get();

        Comment toBeAdded = new Comment();
        toBeAdded.setComment(commentDTO.getComment());
        toBeAdded.setAdvert(advert);
        advert.addComment(toBeAdded);
        toBeAdded.setUser(user);
        user.getCommentList().add(toBeAdded);

        return ServiceResponseModel.ok(commentRepository.save(toBeAdded));
    }

    @Override
    public ServiceResponseModel update(CommentDTO commentDTO) {
        Optional<User> userO = userRepository.findById(commentDTO.getUser_id());
        if(!userO.isPresent())
            return ServiceResponseModel.ClassNotFound(User.class);

        Optional<Advert> advertO = advertRepository.findById(commentDTO.getAdvert_id());
        if(!advertO.isPresent())
            return ServiceResponseModel.ClassNotFound(Advert.class);


        Optional<Comment> commentO = commentRepository.findById(commentDTO.getComment_id());
        if(commentO.isPresent() == false)
            return ServiceResponseModel.ClassNotFound(Comment.class);

        Comment toUpdate = commentO.get();
        toUpdate.setComment(commentDTO.getComment());

        return ServiceResponseModel.ok(commentRepository.save(toUpdate));
    }
}
