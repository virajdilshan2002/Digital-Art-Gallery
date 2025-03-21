package lk.viraj.backend.service.impl;

import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;
import lk.viraj.backend.entity.Item;
import lk.viraj.backend.entity.User;
import lk.viraj.backend.repo.ItemRepository;
import lk.viraj.backend.repo.UserRepository;
import lk.viraj.backend.service.ItemService;
import lk.viraj.backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveItem(ItemDTO itemDTO) {
       itemRepository.save(modelMapper.map(itemDTO, Item.class));
       return VarList.Created;
    }

}
