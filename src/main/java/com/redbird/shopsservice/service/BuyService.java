package com.redbird.shopsservice.service;

import com.redbird.shopsservice.model.*;
import com.redbird.shopsservice.repository.BoughtGoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyService {

    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }

    @Autowired
    private BoughtGoodRepository boughtGoodRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private GoodService goodService;

    public List<BoughtGood> findAll() {
        return boughtGoodRepository.findAll();
    }

    public List<BoughtGood> findByCustomerId(Long id) {
        return boughtGoodRepository.findByCustomerId(id);
    }

    public List<BoughtGood> findByShopName(String shopName) {
        Shop shop = shopService.findByName(shopName);
        if (shop == null) return null;
        return boughtGoodRepository.findByShop(shop);
    }

    public List<BoughtGood> findByGoodName(String name) {
        return boughtGoodRepository.findByName(name);
    }
    public List<BoughtGood> buyGoods(List<BuyGoodDTO> goodDTOList) {
        if (goodDTOList.size() == 0) {
            return null;
        }

        List<Good> goods = new ArrayList<>();
        goodDTOList.forEach(goodDTO -> {
            Good res;
            if (isInEnum(goodDTO.getGoodDTO().getCategory(), Category.class)){
                res = goodService.findGood(
                        goodDTO.getGoodDTO().getName(),
                        goodDTO.getGoodDTO().getDescription(),
                        goodDTO.getGoodDTO().getCost(),
                        goodDTO.getGoodDTO().getShopName(),
                        Category.valueOf(goodDTO.getGoodDTO().getCategory()));
                // if find and amount is ok
                if (res != null && (res.getAmount() > goodDTO.getGoodDTO().getAmount())) {
                    //set amount here to distinct later
                    res.setAmount(goodDTO.getGoodDTO().getAmount());
                    goods.add(res);
                }
            }
        });
        // if all goods are pass checks and converted
        if (goods.size() < goodDTOList.size()) {
            return null;
        }

        // save boughtList and make response
        Long customerId = goodDTOList.get(0).getCustomerId();
        ZonedDateTime time = ZonedDateTime.now();
        List<BoughtGood> boughtGoodList = new ArrayList<>();

        goods.forEach(good -> {
            BoughtGood boughtGood = new BoughtGood();
            boughtGood.setName(good.getName());
            boughtGood.setDescription(good.getDescription());
            boughtGood.setCost(good.getCost());
            boughtGood.setShop(good.getShop());
            boughtGood.setCategory(good.getCategory());
            boughtGood.setCustomerId(customerId);
            boughtGood.setAmount(good.getAmount());
            boughtGood.setBoughtTime(time);


            Good tmp = goodService.findById(good.getId());
            tmp.setAmount(tmp.getAmount() - good.getAmount());
            boughtGoodList.add(boughtGood);
            goodService.saveGood(tmp);
        });

        return boughtGoodRepository.saveAll(boughtGoodList);
    }

}
