/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.chatproject.server.module.dal.hibernate.dao.imp;

import com.jets.chatproject.server.module.dal.dao.PicturesDao;
import com.jets.chatproject.server.module.dal.entities.Picture;
import com.jets.chatproject.server.module.dal.hibernate.entity.Pictures;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author ibrahim
 */
public class PicturesDaoImp implements PicturesDao {

    Session session;

    public PicturesDaoImp(Session session) {
        this.session = session;
    }

    private Picture mapPicture(Pictures picture) {
        return new Picture(picture.getPictureId(), picture.getPicture());
    }

    @Override
    public Picture findById(int id) throws Exception {
        Criteria criteria = session.createCriteria(Pictures.class);
        criteria.add(Restrictions.eq("pictureId", id));
        Pictures picture = (Pictures) criteria.uniqueResult();
        if (picture == null) {
            return null;
        } else {
            return mapPicture(picture);
        }
    }

    @Override
    public int insert(Picture picture) throws Exception {
        try {
            Pictures pic = new Pictures(picture.getData());
            session.beginTransaction();
            session.persist(pic);
            session.getTransaction().commit();
            return pic.getPictureId();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public boolean update(Picture picture) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Picture object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
