package kr.sbcommon.financialledger.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto createCategory(CreateCategoryRequestDto dto) {
        Category saved = categoryRepository.save(Category.of(dto.getName(), dto.getType(), dto.getPrint_seq(), dto.getIsUsable()));
        return CategoryDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .type(saved.getType())
                .print_seq(saved.getPrint_seq())
                .isUsable(saved.getIsUsable())
                .build();
    }
}
