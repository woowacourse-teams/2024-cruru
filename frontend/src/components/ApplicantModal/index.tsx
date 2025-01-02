import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useSpecificProcessId } from '@contexts/SpecificProcessIdContext';

import useTab from '@components/_common/molecules/Tab/useTab';
import { FiFileText, FiMail } from 'react-icons/fi';
import BaseModal from './BaseModal';

import ApplicantBaseInfo from './ApplicantBaseInfo';
import QuestionSection from './ApplicantDetailInfo/QuestionSection';
import ApplicantEvalInfo from './ApplicantEvalInfo';
import EvaluationHeader from './ApplicantEvalInfo/EvaluationHeader';
import InquireEvalHeader from './InquireEvalHeader';
import ApplicantModalHeader from './ModalHeader';

import S from './style';
import usePaginatedEvaluation from './usePaginatedEvaluation';
import EmailHistorySection from './ApplicantDetailInfo/EmailHistorySection';

export type ApplicantModalTabItems = '지원서' | '이메일';

const TabMenus = {
  지원서: {
    title: '지원서',
    icon: FiFileText,
    description: '지원 시 접수된 지원서 내용입니다.',
  },
  이메일: {
    title: '이메일',
    icon: FiMail,
    description: '지원자에게 전송한 이메일 내역입니다.',
  },
} as const;

export default function ApplicantModal() {
  const { applicantId } = useSpecificApplicantId();
  const { processId } = useSpecificProcessId();

  const { currentMenu, moveTabByParam } = useTab<ApplicantModalTabItems>({ defaultValue: '지원서' });
  const { currentProcess, isCurrentProcess, moveProcess, isLastProcess, isFirstProcess } =
    usePaginatedEvaluation(processId);

  if (!applicantId || !processId) return null;

  return (
    <BaseModal>
      <S.Container>
        <S.ModalHeader>
          <ApplicantModalHeader title="지원자 상세" />
        </S.ModalHeader>

        <S.ModalSidebar>
          <ApplicantBaseInfo applicantId={applicantId} />
          <S.ModalMenus>
            <S.ModalMenusTitle>메뉴</S.ModalMenusTitle>
            {Object.values(TabMenus).map((menu) => (
              <S.ModalMenusItem
                key={menu.title}
                isSelected={currentMenu === menu.title}
                onClick={() => moveTabByParam(menu.title)}
              >
                <menu.icon size={20} />
                {menu.title}
              </S.ModalMenusItem>
            ))}
          </S.ModalMenus>
        </S.ModalSidebar>

        <S.ModalNav>
          <S.ModalNavHeaderContainer>
            <S.ModalNavHeader>{TabMenus[currentMenu].title}</S.ModalNavHeader>
            <S.ModalNavContent>{TabMenus[currentMenu].description}</S.ModalNavContent>
          </S.ModalNavHeaderContainer>
        </S.ModalNav>

        <S.ModalMain>
          {currentMenu === '지원서' && <QuestionSection applicantId={applicantId} />}
          {currentMenu === '이메일' && <EmailHistorySection applicantId={applicantId} />}
        </S.ModalMain>

        <S.ModalEvalHeader>
          <EvaluationHeader
            title="지원자 평가"
            description="지원자 평가에 대한 내용입니다."
          />
        </S.ModalEvalHeader>

        <S.ModalAsideHeader>
          <InquireEvalHeader
            isLastProcess={isLastProcess}
            isFirstProcess={isFirstProcess}
            isCurrentProcess={isCurrentProcess}
            handleChangeProcess={moveProcess}
            processName={currentProcess.processName}
          />
        </S.ModalAsideHeader>

        <S.ModalAside>
          <ApplicantEvalInfo
            applicantId={applicantId}
            processId={currentProcess.processId}
            isCurrentProcess={isCurrentProcess}
          />
        </S.ModalAside>
      </S.Container>
    </BaseModal>
  );
}
