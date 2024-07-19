import { Meta, StoryObj } from '@storybook/react';
import BaseModal from './index';
import { ModalProvider, useModal } from '@/context/ModalContext';
import Button from '../Button';

const meta: Meta<typeof BaseModal> = {
  title: 'Components/BaseModal',
  component: BaseModal,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'Provider로 감싸지 않으면 useModal이 작동하지 않습니다. 가운데 위치한 상태만 구현되었습니다.',
      },
    },
  },
  tags: ['autodocs'],
} satisfies Meta<typeof BaseModal>;

export default meta;
type Story = StoryObj<typeof meta>;

function ModalToggleComponent() {
  const { isOpen, open, close } = useModal();

  return (
    <div>
      <Button
        type="button"
        size="md"
        color="white"
        onClick={isOpen ? close : open}
      >
        {isOpen ? '닫혀줬으면 해...' : '열려라 참깨'}
      </Button>
      <BaseModal>
        <p>여기에 모달 컨텐츠가 들어갑니다.</p>
      </BaseModal>
    </div>
  );
}

export const Default: Story = {
  render: () => (
    <ModalProvider>
      <ModalToggleComponent />
    </ModalProvider>
  ),
};
