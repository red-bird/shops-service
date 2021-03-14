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

    public List<BoughtGoodDTO> findAll() {
        return convertToDTOList(boughtGoodRepository.findAll());
    }

    public BoughtGoodDTO findById(Long id) {
        return convertToDTO(boughtGoodRepository.findById(id).orElse(null));
    }

    public List<BoughtGoodDTO> findByCustomerId(Long id) {
        return convertToDTOList(boughtGoodRepository.findByCustomerId(id));
    }

    public List<BoughtGoodDTO> findByShopName(String shopName) {
        Shop shop = shopService.findByName(shopName);
        if (shop == null) return null;
        return convertToDTOList(boughtGoodRepository.findByShop(shop));
    }

    public List<BoughtGoodDTO> findByGoodName(String name) {
        return convertToDTOList(boughtGoodRepository.findByName(name));
    }

    public synchronized List<BoughtGoodDTO> buyGoods(List<BuyGoodDTO> goodDTOList) {
        if (goodDTOList.size() == 0) {
            return null;
        }

        List<Good> goods = new ArrayList<>();
        goodDTOList.forEach(goodDTO -> {
            Good res;
            if (isInEnum(goodDTO.getGoodDTO().getCategory(), Category.class)){
                res = goodService.findGoodWithoutCategory(
                        goodDTO.getGoodDTO().getName(),
                        goodDTO.getGoodDTO().getDescription(),
                        goodDTO.getGoodDTO().getCost(),
                        goodDTO.getGoodDTO().getShopName());
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
            BoughtGood boughtGood = convertToBoughtGood(good, customerId, time);

            Good tmp = goodService.findById(good.getId());
            tmp.setAmount(tmp.getAmount() - good.getAmount());
            boughtGoodList.add(boughtGood);
            goodService.saveGood(tmp);
        });
        boughtGoodRepository.saveAll(boughtGoodList);
        List<BoughtGoodDTO> boughtGoodDTOList = convertToDTOList(boughtGoodList);
        return categoryChanger(boughtGoodDTOList, goodDTOList);
    }


    public List<BoughtGoodDTO> categoryChanger(List<BoughtGoodDTO> boughtGoodDTOList, List<BuyGoodDTO> goodDTOList) {
        for (int i = 0; i < boughtGoodDTOList.size(); i++) {
            boughtGoodDTOList.get(i).setCategory(goodDTOList.get(i).getGoodDTO().getCategory());
        }
        return boughtGoodDTOList;
    }

    // converters

    public List<BoughtGoodDTO> convertToDTOList(List<BoughtGood> boughtGoodList) {
        if (boughtGoodList == null) return null;
        List<BoughtGoodDTO> boughtGoodDTOList = new ArrayList<>();
        boughtGoodList.forEach(boughtGood -> {
            boughtGoodDTOList.add(convertToDTO(boughtGood));
        });
        return boughtGoodDTOList;
    }

    public BoughtGoodDTO convertToDTO(BoughtGood boughtGood) {
        if (boughtGood == null) return null;
        BoughtGoodDTO boughtGoodDTO = new BoughtGoodDTO();
        boughtGoodDTO.setName(boughtGood.getName());
        boughtGoodDTO.setDescription(boughtGood.getDescription());
        boughtGoodDTO.setCost(boughtGood.getCost());
        boughtGoodDTO.setShopName(boughtGood.getShop().getName());
        boughtGoodDTO.setCategory(boughtGood.getCategory().toString());
        boughtGoodDTO.setCustomerId(boughtGood.getCustomerId());
        boughtGoodDTO.setAmount(boughtGood.getAmount());
        boughtGoodDTO.setBoughtTime(boughtGood.getBoughtTime());
        return boughtGoodDTO;
    }

    public BoughtGood convertToBoughtGood(Good good, Long customerId, ZonedDateTime time) {
        if (good == null) return null;
        BoughtGood boughtGood = new BoughtGood();
        boughtGood.setName(good.getName());
        boughtGood.setDescription(good.getDescription());
        boughtGood.setCost(good.getCost());
        boughtGood.setShop(good.getShop());
        boughtGood.setCategory(good.getCategory());
        boughtGood.setCustomerId(customerId);
        boughtGood.setAmount(good.getAmount());
        boughtGood.setBoughtTime(time);
        return boughtGood;
    }
}
