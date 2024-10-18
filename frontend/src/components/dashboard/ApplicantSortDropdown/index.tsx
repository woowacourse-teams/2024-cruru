import Dropdown from '@components/_common/molecules/Dropdown';
import DropdownItemRenderer from '@components/_common/molecules/DropdownItemRenderer';
import { ProcessSortOption, SortOption } from '@customTypes/process';
import { SortOptionState } from '@hooks/useProcess/useSortApplicant';

interface ApplicantSortDropdownProps {
  sortOption: SortOptionState;
  updateSortOption: (option?: SortOptionState) => void;
}

interface SortOptionsType {
  id: ProcessSortOption;
  name: string;
  value: SortOption;
}

const sortOptions: SortOptionsType[] = [
  { id: 'sortByScore', name: '평점 높은 순', value: 'DESC' },
  { id: 'sortByScore', name: '평점 낮은 순', value: 'ASC' },
  { id: 'sortByCreatedAt', name: '지원일 최신 순', value: 'DESC' },
  { id: 'sortByCreatedAt', name: '지원일 오래된 순', value: 'ASC' },
];

export default function ApplicantSortDropdown({ sortOption, updateSortOption }: ApplicantSortDropdownProps) {
  const selectedOption = sortOptions.find(
    (option) => sortOption && option.id && option.value === sortOption[option.id],
  );

  return (
    <Dropdown
      initValue={selectedOption?.name || '정렬'}
      size="sm"
      isShadow={false}
      width={120}
    >
      <DropdownItemRenderer
        items={sortOptions.map(({ id, name, value }) => ({
          type: 'clickable',
          id,
          name,
          onClick: () => updateSortOption({ [id]: value } as SortOptionState),
        }))}
      />
    </Dropdown>
  );
}
