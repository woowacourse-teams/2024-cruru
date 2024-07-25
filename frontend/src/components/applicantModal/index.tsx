import BaseModal from '@components/common/BaseModal';
import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import ApplicantBaseInfo from './ApplicantBaseInfo';

import ModalHeader from '../common/ModalHeader';
import AppDetailHeader from './ApplicantDetailInfo/AppDetailHeader';
import QuestionSection from './ApplicantDetailInfo/QuestionSection';
import S from './style';

export default function ApplicantModal() {
  const { applicantId } = useSpecificApplicantId();
  if (!applicantId) return null;

  const headerTabs = [
    {
      id: 0,
      name: '지원서',
      onClick: () => console.log('지원서가 클릭되었습니다.'),
    },
    {
      id: 1,
      name: '이력서',
      onClick: () => console.log('이력서가 클릭되었습니다.'),
    },
  ];

  return (
    <BaseModal>
      <S.Container>
        <S.ModalHeader>
          <ModalHeader title="지원서" />
        </S.ModalHeader>

        <S.ModalSidebar>
          <ApplicantBaseInfo applicantId={applicantId} />
        </S.ModalSidebar>

        <S.ModalNav>
          <AppDetailHeader
            headerTabs={headerTabs}
            activeTabId={0}
            content="지원 시 접수된 지원서 내용입니다."
          />
        </S.ModalNav>

        <S.ModalMain>
          <QuestionSection applicantId={applicantId} />
        </S.ModalMain>

        <S.ModalAside>{/*지원자 평가 항목 폼이 들어갑니다 */}</S.ModalAside>
      </S.Container>
    </BaseModal>
  );
}
