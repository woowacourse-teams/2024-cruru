import { useParams } from 'react-router-dom';
import { useEffect } from 'react';

import useProcess from '@hooks/useProcess';

import InputField from '@components/_common/molecules/InputField';
import ApplicantSortDropdown from '../ApplicantSortDropdown';

import { useSearchApplicant } from '../useSearchApplicant';

import S from './style';

interface DashboardFunctionTabProps {
  onSearchName: (name: string) => void;
}

export default function DashboardFunctionTab({ onSearchName }: DashboardFunctionTabProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };

  const { debouncedName, name, updateName } = useSearchApplicant();
  const { sortOption, updateSortOption } = useProcess({ dashboardId, applyFormId });

  useEffect(() => {
    onSearchName(debouncedName);
  }, [onSearchName, debouncedName]);

  return (
    <S.Wrapper>
      <S.FunctionsContainer>
        <InputField
          type="search"
          placeholder="지원자 이름 검색"
          value={name}
          onChange={(e) => updateName(e.target.value)}
        />
        <ApplicantSortDropdown
          sortOption={sortOption}
          updateSortOption={updateSortOption}
        />
      </S.FunctionsContainer>
    </S.Wrapper>
  );
}
