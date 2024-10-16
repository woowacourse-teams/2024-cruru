import Dropdown from '@components/_common/molecules/Dropdown';
import DropdownItemRenderer, { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';
import { ProcessSortOption } from '@customTypes/process';
import { Entries } from '@customTypes/utilTypes';

interface ApplicantSortDropdownProps {
  sortOption: ProcessSortOption | undefined;
  updateSortOption: (option?: ProcessSortOption) => void;
}

type DropdownSortOptions = {
  sortByScore: string;
  sortByCreatedAt: string;
};

const sortOptions: DropdownSortOptions = {
  sortByScore: '평점 높은 순',
  sortByCreatedAt: '지원 날짜 순',
};

export default function ApplicantSortDropdown({ sortOption, updateSortOption }: ApplicantSortDropdownProps) {
  return (
    <Dropdown
      initValue={sortOption ? sortOptions[sortOption] : '정렬'}
      size="sm"
      isShadow={false}
      width={100}
    >
      <DropdownItemRenderer
        items={[
          { type: 'clickable', id: 'all', name: '전체', onClick: () => updateSortOption() },
          ...((Object.entries(sortOptions) as Entries<DropdownSortOptions>).map(([id, name]) => ({
            type: 'clickable',
            id,
            name,
            onClick: () => updateSortOption(id),
          })) as DropdownItemType[]),
        ]}
      />
    </Dropdown>
  );
}
